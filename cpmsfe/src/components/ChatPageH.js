import {
  Avatar,
  Button,
  Comment,
  Form,
  Input,
  List,
  Tooltip,
  notification,
} from "antd";
import { HeartOutlined, HeartFilled } from "@ant-design/icons";
import moment from "moment";
import React, { useState, useEffect } from "react";
import {
  chat,
  createChat,
  deleteChat,
  likeChat,
  replyChat,
  showreplyChat,
  stickChat,
  unstickChat,
  likedChat,
  unlikeChat,
} from "../utils";
import "../index.css";
import "antd/dist/antd.css";

const { TextArea } = Input;

const LikeButton = ({ isLiked, likes, onLike }) => (
  <span onClick={onLike} className={isLiked ? "liked-heart" : ""}>
    {isLiked ? <HeartFilled /> : <HeartOutlined />} ({likes})
  </span>
);

const CommentList = ({
  comments,
  setComments,
  handleToggleFavorite,
  handleDeleteComment,
  handleDeleteReply,
  handleLikeComment,
  handleReplyContentChange,
  handleToggleReply,
  handleReply,
  handlePinComment,
  handleUnpinComment,
  currentUser,
  isNested = false,
}) => {
  const [repliesVisibility, setRepliesVisibility] = useState({});
  const [likedComments, setLikedComments] = useState(new Set());
  const [comment, setComment] = useState([]);

  useEffect(() => {
    fetchLikedComments();
  }, []);

  const fetchLikedComments = async () => {
    try {
      const likedCommentIds = await likedChat();
      setLikedComments(new Set(likedCommentIds));
    } catch (error) {
      console.error("Error fetching liked comments:", error);
    }
  };

  const handleToggleReplies = async (commentId) => {
    try {
      if (!repliesVisibility[commentId]) {
        const replies = await showreplyChat(commentId);
        setRepliesVisibility({
          ...repliesVisibility,
          [commentId]: replies,
        });
      } else {
        setRepliesVisibility({
          ...repliesVisibility,
          [commentId]: undefined,
        });
      }
    } catch (error) {
      console.error(error);
    }
  };

  const toggleLikeComment = async (commentId) => {
    try {
      setLikedComments((prevLikedComments) => {
        const newLikedComments = new Set(prevLikedComments);
        if (newLikedComments.has(commentId)) {
          newLikedComments.delete(commentId);
        } else {
          newLikedComments.add(commentId);
        }
        return newLikedComments;
      });

      let updatedComments;
      if (likedComments.has(commentId)) {
        await unlikeChat(commentId);
        updatedComments = comments.map((comment) =>
          comment.id === commentId
            ? { ...comment, likes: comment.likes - 1 }
            : comment
        );
      } else {
        await likeChat(commentId);
        updatedComments = comments.map((comment) =>
          comment.id === commentId
            ? { ...comment, likes: comment.likes + 1 }
            : comment
        );
      }
      setComment(updatedComments);

      window.location.reload();
    } catch (error) {
      console.error("Error toggling like state:", error);
    }
  };

  return (
    <List
      dataSource={comments}
      // header={`${comments.length} ${comments.length > 1 ? "replies" : "reply"}`}
      itemLayout="horizontal"
      renderItem={(comment) => (
        <Comment
          actions={[
            ...(!isNested
              ? [
                  <span
                    key="comment-show-replies"
                    onClick={() => handleToggleReplies(comment.id)}
                  >
                    {repliesVisibility[comment.id]
                      ? "Hide Replies"
                      : "Show Replies"}
                  </span>,
                  !comment.isReply && (
                    <span
                      key="comment-pin-unpin"
                      onClick={() => {
                        comment.stick_to_top
                          ? handleUnpinComment(comment.id)
                          : handlePinComment(comment.id);
                      }}
                    >
                      {comment.stick_to_top ? "Unpin" : "Pin"}
                    </span>
                  ),
                  <LikeButton
                    isLiked={likedComments.has(comment.id)}
                    likes={comment.likes}
                    onLike={() => toggleLikeComment(comment.id)}
                  />,
                ]
              : [
                  <LikeButton
                    isLiked={likedComments.has(comment.id)}
                    likes={comment.likes}
                    onLike={() => toggleLikeComment(comment.id)}
                  />,
                ]),
            <span
              key="comment-delete"
              style={{
                display: comment.username !== currentUser ? "inline" : "none",
              }}
              onClick={() =>
                isNested
                  ? handleDeleteReply(
                      comment.replyTo,
                      comment.id,
                      comment.replyTo,
                      comment.username
                    )
                  : handleDeleteComment(comment.id, comment.username)
              }
            >
              Delete
            </span>,

            !isNested && !comment.isReply && (
              <div style={{ display: "block" }} key="comment-nested-reply-to">
                {!comment.isReplying ? (
                  <Button onClick={() => handleToggleReply(comment.id)}>
                    Reply
                  </Button>
                ) : (
                  <>
                    <TextArea
                      rows={2}
                      placeholder="Reply..."
                      value={comment.replyContent}
                      onChange={(e) =>
                        handleReplyContentChange(e.target.value, comment.id)
                      }
                    />
                    <Button onClick={() => handleReply(comment.id)}>
                      Add Reply
                    </Button>
                  </>
                )}
              </div>
            ),
          ]}
          avatar={<Avatar src={comment.avatar} alt={comment.author} />}
          author={<p>{comment.username}</p>}
          content={<p>{comment.content}</p>}
          datetime={
            <Tooltip title={comment.datetime}>
              {moment(comment.datetime).fromNow()}
            </Tooltip>
          }
        >
          {/* Conditionally render the list of replies */}
          {repliesVisibility[comment.id] &&
            repliesVisibility[comment.id] !== true && (
              <CommentList
                comments={repliesVisibility[comment.id]}
                handleToggleFavorite={handleToggleFavorite}
                handleDeleteComment={handleDeleteComment}
                handleDeleteReply={handleDeleteReply}
                handleLikeComment={handleLikeComment}
                handleReplyContentChange={handleReplyContentChange}
                handleToggleReply={handleToggleReply}
                handleReply={handleReply}
                handlePinComment={handlePinComment}
                handleUnpinComment={handleUnpinComment}
                currentUser={currentUser}
                isNested
                toggleLikeComment={toggleLikeComment}
                likedComments={likedComments}
              />
            )}
        </Comment>
      )}
    />
  );
};

const ChatPage = () => {
  const [form] = Form.useForm();
  const [comments, setComments] = useState([]);
  const [replyFormValues, setReplyFormValues] = useState({});
  const [currentUser, setCurrentUser] = useState("");

  useEffect(() => {
    // Fetch the current user from localStorage
    const storedUser = localStorage.getItem("username");
    setCurrentUser(storedUser);
    fetchComments();
  }, []);

  const fetchComments = async () => {
    try {
      let commentsData = await chat();
      commentsData = commentsData.map((comment) => ({
        ...comment,
        isReplying: false,
        datetime: comment.posted_time,
      }));
      console.log("Current User:", currentUser);

      setComments(commentsData);
    } catch (error) {
      console.error(error);
    }
  };

  const handleReply = async (commentId) => {
    const replyContent = replyFormValues[commentId];
    if (replyContent) {
      try {
        const newReply = {
          content: replyContent,
          posted_time: moment().format("YYYY-MM-DD HH:mm:ss"),
          reply_to: commentId,
        };
        console.log(newReply);

        const updatedComments = await replyChat(commentId, newReply);
        setComments(updatedComments);
        setReplyFormValues((prevState) => {
          const newState = { ...prevState };
          delete newState[commentId];
          return newState;
        });

        // Fetch all comments again after submitting the reply
        fetchComments();
      } catch (error) {
        console.error(error);
      }
    }
  };

  const handleToggleFavorite = async (commentId) => {
    try {
      const updatedComments = await likeChat(commentId); // Call the API to toggle favorite
      setComments(updatedComments);
    } catch (error) {
      console.error(error);
    }
  };

  const handlePinComment = async (commentId) => {
    try {
      const updatedComments = await stickChat(commentId);
      setComments(updatedComments);
      fetchComments();
    } catch (error) {
      console.error(error);
    }
  };

  const handleUnpinComment = async (commentId) => {
    try {
      const updatedComments = await unstickChat(commentId);
      setComments(updatedComments);
      fetchComments();
    } catch (error) {
      console.error(error);
    }
  };

  const handleReplyContentChange = (value, commentId) => {
    setReplyFormValues({
      ...replyFormValues,
      [commentId]: value,
    });
  };

  const handleToggleReply = (commentId) => {
    setComments((prevComments) =>
      prevComments.map((comment) =>
        comment.id === commentId
          ? { ...comment, isReplying: !comment.isReplying }
          : comment
      )
    );
  };

  const handleDeleteComment = async (id, author, replyTo) => {
    try {
      if (author !== currentUser) {
        notification.error({
          message: "Error",
          description: "You can only delete your own comments.",
        });
        return;
      }
      setComments((prevComments) =>
        prevComments.filter((comment) => comment.id !== id)
      );
      const response = await deleteChat(id, replyTo); // update deleteChat to accept replyTo

      if (!response.success) {
        throw new Error(response.message || "Failed to delete comment");
      }
      // ... Rest of the code
    } catch (error) {
      console.error(error);
      notification.error({
        message: "Error",
        description: "Failed to delete comment",
      });
    }
  };

  const handleDeleteReply = async (commentId, replyId, replyTo, author) => {
    try {
      console.log(author);
      if (author !== currentUser) {
        notification.error({
          message: "Error",
          description: "You can only delete your own replies.",
        });
        return;
      }
      const response = await deleteChat(replyId, replyTo);
      if (!response.success) {
        throw new Error(response.message || "Failed to delete reply");
      }
      setComments((prevComments) =>
        prevComments.map((comment) =>
          comment.id === commentId
            ? {
                ...comment,
                replies: [...comment.replies, { id: replyId, replyTo, author }], // revert the reply back
              }
            : comment
        )
      );
    } catch (error) {
      console.error(error);
      notification.error({
        message: "Error",
        description: "Failed to delete reply",
      });
    }
  };

  const handleSubmit = async (values) => {
    try {
      const formattedDateTime = moment().format("YYYY-MM-DD HH:mm:ss");

      const postData = {
        content: values.comment,
        posted_time: formattedDateTime,
        reply_to: values.replyTo || null, // Include the replyTo value or null if not available
      };

      const updatedComments = await createChat(postData);

      setComments(updatedComments);

      form.resetFields();
      fetchComments();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      {comments.length > 0 && (
        <CommentList
          comments={comments}
          handleToggleFavorite={handleToggleFavorite}
          handleDeleteComment={handleDeleteComment}
          handleDeleteReply={handleDeleteReply}
          handleReplyContentChange={handleReplyContentChange}
          handleToggleReply={handleToggleReply}
          handleReply={handleReply}
          handlePinComment={handlePinComment}
          handleUnpinComment={handleUnpinComment}
        />
      )}
      <Form form={form} onFinish={handleSubmit}>
        <Form.Item name="comment" rules={[{ required: true }]}>
          <TextArea
            rows={4}
            placeholder="Enter your comment"
            style={{ width: "35%", float: "left", display: "block" }}
          />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Add Comment
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default ChatPage;

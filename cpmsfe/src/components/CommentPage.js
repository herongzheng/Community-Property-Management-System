import {
  Avatar,
  Comment,
  List,
  message,
  Button,
  Form,
  Input,
  Space,
  Modal,
} from "antd";
import React from "react";
import { useState, useEffect } from "react";
import {
  showReplyChat,
  chat,
  createChat,
  replyChat,
  likedChat,
  likeChat,
  unlikeChat,
  deleteChat,
  stickChat,
  unstickChat,
} from "../utils";
import moment from "moment";
import { LikeOutlined, LikeFilled } from "@ant-design/icons";

// the compoennt to add a comment
const { TextArea } = Input;
const Editor = ({ onChange, onSubmit, submitting, value }) => (
  <>
    <Form.Item>
      <TextArea rows={1} onChange={onChange} value={value} />
    </Form.Item>
    <Form.Item>
      <Button
        htmlType="submit"
        loading={submitting}
        onClick={onSubmit}
        type="primary"
      >
        Add Comment
      </Button>
    </Form.Item>
  </>
);

const CommentDeletionModal = ({
  onFinish,
  deleteModalVisible,
  cancelDelete,
}) => {
  return (
    <>
      <Modal
        destroyOnClose={true}
        title="Delete comment"
        centered={true}
        visible={deleteModalVisible}
        footer={null}
        onCancel={cancelDelete}
        closable={false}
      >
        <Form
          preserve={false}
          labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          onFinish={onFinish}
        >
          <h4>Delete your comment permanently?</h4>
          <br />
          <Form.Item>
            <Button
              type="primary"
              onClick={cancelDelete}
              style={{ marginLeft: "120px" }}
            >
              Cancel
            </Button>
            <Button
              type="default"
              htmlType="submit"
              style={{ marginLeft: "50px" }}
            >
              Delete
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

function CommentPage() {
  const [dataL1, setDataL1] = useState([]);
  const [dataL2, setDataL2] = useState({});
  const [loading1, setLoading1] = useState(false);
  const [loading2, setLoading2] = useState(false);
  const [expandStates, setExpandStates] = useState({});
  const [replyingStates, setReplyingStates] = useState({});
  const [thumbsupStates, setThumbsupStates] = useState(new Set());
  const [thumbsupCounts, setThumbsupCounts] = useState({});
  const [deleteModalVisible, setDeleteModalVisible] = useState(false);
  const [modalId, setModalId] = useState(null);
  const [pinStates, setPinStates] = useState(new Set());

  const [newCommentValue, setNewCommentValue] = useState("");

  // handlers for submitting a 1st level comment
  const handleChange = (e) => {
    setNewCommentValue(e.target.value);
  };

  const handleSubmit = async () => {
    if (newCommentValue.length === 0) return;
    try {
      const postData = {
        content: newCommentValue,
        posted_time: moment().format("YYYY-MM-DD HH:mm:ss"),
        reply_to: null,
      };
      await createChat(postData);
      message.success(`Post successfully`);
    } catch (error) {
      message.error(error.message);
    } finally {
      setLoading1(true);
      setNewCommentValue("");
    }
  };

  const handleSubmitReply = async (id) => {
    if (newCommentValue.length === 0) return;
    try {
      const postData = {
        content: newCommentValue,
        posted_time: moment().format("YYYY-MM-DD HH:mm:ss"),
        reply_to: id,
      };
      const replyList = await replyChat(id, postData);
      setDataL2({ ...dataL2, [id]: replyList });
      message.success(`Post successfully`);
      setThumbsupCounts(
        replyList.reduce(
          (acc, cur) => ({
            ...acc,
            [cur.id]: cur.likes,
          }),
          { ...thumbsupCounts }
        )
      );
    } catch (error) {
      message.error(error.message);
    } finally {
      //   setLoading2(true);
      setReplyingStates({ ...replyingStates, [id]: false });
      setNewCommentValue("");
    }
  };

  useEffect(() => {
    chat()
      .then((data) => {
        setDataL1(data);
        setThumbsupCounts(
          data.reduce(
            (acc, cur) => ({
              ...acc,
              [cur.id]: cur.likes,
            }),
            { ...thumbsupCounts }
          )
        );
        setPinStates(
          new Set(data.filter((e) => e.stick_to_top).map((e) => e.id))
        );
      })
      .catch((err) => {
        message.error(err.message);
      });

    likedChat()
      .then((data) => {
        setThumbsupStates(new Set(data));
      })
      .catch((err) => {
        message.error(err.message);
      });

    setLoading1(false);
  }, [loading1]);

  useEffect(() => {
    setLoading2(false);
  }, [expandStates, replyingStates, thumbsupStates, thumbsupCounts]);

  const toggleReplies = async (id, isExpanded) => {
    if (!isExpanded) {
      try {
        const replies = await showReplyChat(id);
        setDataL2({ ...dataL2, [id]: replies });
        setExpandStates({ ...expandStates, [id]: true });
        setThumbsupCounts(
          replies.reduce(
            (acc, cur) => ({
              ...acc,
              [cur.id]: cur.likes,
            }),
            { ...thumbsupCounts }
          )
        );
      } catch (error) {
        message.error(error.message);
      } finally {
        setLoading2(true);
      }
    } else {
      setExpandStates({ ...expandStates, [id]: false });
    }
  };

  const toggleAddReply = (id, isReplying) => {
    if (!isReplying) {
      setReplyingStates({ ...replyingStates, [id]: true });
    } else {
      setReplyingStates({ ...replyingStates, [id]: false });
    }
  };

  const toggleLike = async (id, isLiked) => {
    if (!isLiked) {
      try {
        await likeChat(id);
        let newState = new Set(thumbsupStates);
        newState.add(id);
        setThumbsupStates(newState);
        let tempState = thumbsupCounts;
        tempState[`${id}`] += 1;
        setThumbsupCounts(tempState);
      } catch (error) {
        message.error(error.message);
      } finally {
      }
    } else {
      try {
        await unlikeChat(id);
        let newState = new Set(thumbsupStates);
        newState.delete(id);
        setThumbsupStates(newState);
        let tempState = thumbsupCounts;
        tempState[`${id}`] -= 1;
        setThumbsupCounts(tempState);
      } catch (error) {
        message.error(error.message);
      } finally {
      }
    }
  };

  const togglePin = async (id, isPinned) => {
    if (!isPinned) {
      try {
        const resp = await stickChat(id);
        setDataL1(resp);
        let newState = new Set(pinStates);
        newState.add(id);
        setPinStates(newState);
      } catch (error) {
        message.error(error.message);
      } finally {
      }
    } else {
      try {
        const resp = await unstickChat(id);
        setDataL1(resp);
        let newState = new Set(pinStates);
        newState.delete(id);
        setPinStates(newState);
      } catch (error) {
        message.error(error.message);
      } finally {
      }
    }
  };

  const cancelDelete = () => {
    setDeleteModalVisible(false);
    setModalId(null);
  };

  const onDelete = (id) => {
    setModalId(id);
    setDeleteModalVisible(true);
  };

  const handleDeleteComment1 = async (id) => {
    try {
      await deleteChat(id);
    } catch (error) {
      message.error(error.message);
    } finally {
      setLoading1(true);
      setModalId(null);
    }
  };

  const handleDeleteComment2 = async (id1, id2) => {
    try {
      await deleteChat(id2);
    } catch (error) {
      message.error(error.message);
    }

    try {
      const replies = await showReplyChat(id1);
      setDataL2({ ...dataL2, [id1]: replies });
      setThumbsupCounts(
        replies.reduce(
          (acc, cur) => ({
            ...acc,
            [cur.id]: cur.likes,
          }),
          { ...thumbsupCounts }
        )
      );
    } catch (error) {
      message.error(error.message);
    } finally {
      setLoading2(true);
      setModalId(null);
    }
  };

  return (
    <>
      <Comment
        author={
          <span style={{ color: "black" }}>
            <b>{localStorage.getItem("username")}</b>
          </span>
        }
        avatar={
          <Avatar
            src="https://img.freepik.com/free-psd/3d-icon-social-media-app_23-2150049569.jpg?w=740&t=st=1694752925~exp=1694753525~hmac=9f7cafd64ea5660940fd27018077159cd1115668df4f6ac95e5127faec762e83"
            alt="Han Solo"
          />
        }
        content={
          <Editor
            onChange={handleChange}
            onSubmit={handleSubmit}
            submitting={loading1}
            value={newCommentValue}
          />
        }
      />
      <List
        //   itemLayout="vertical"
        loading={loading1}
        dataSource={dataL1}
        renderItem={(commentL1) => (
          <Comment
            author={
              pinStates.has(commentL1.id) ? (
                <span style={{ color: "black" }}>
                  <b>📌 {commentL1.user.username}</b>
                </span>
              ) : (
                <span style={{ color: "black" }}>
                  <b>{commentL1.user.username}</b>
                </span>
              )
            }
            avatar={
              <Avatar
                src="https://img.freepik.com/free-psd/3d-icon-social-media-app_23-2150049569.jpg?w=740&t=st=1694752925~exp=1694753525~hmac=9f7cafd64ea5660940fd27018077159cd1115668df4f6ac95e5127faec762e83"
                alt="Han Solo"
              />
            }
            datetime={
              <span style={{ color: "black" }}>
                {moment(commentL1.posted_time).fromNow()}
              </span>
            }
            content={commentL1.content}
            actions={[
              <span style={{ display: "table-cell", vertical_align: "middle" }}>
                <div style={{ display: "grid" }}>
                  <div style={{ grid_row: 1 }}>
                    <Space size={15}>
                      <span
                        onClick={() =>
                          toggleLike(
                            commentL1.id,
                            thumbsupStates.has(commentL1.id)
                          )
                        }
                      >
                        {thumbsupStates.has(commentL1.id) ? (
                          <LikeFilled />
                        ) : (
                          <LikeOutlined />
                        )}{" "}
                        {thumbsupCounts[`${commentL1.id}`]}
                      </span>
                      {localStorage.getItem("asHost") === "true" && (
                        <span
                          onClick={() =>
                            togglePin(commentL1.id, pinStates.has(commentL1.id))
                          }
                        >
                          {pinStates.has(commentL1.id) ? "Unpin" : "pin"}
                        </span>
                      )}
                      {commentL1.user.username ===
                        localStorage.getItem("username") && (
                        <span onClick={() => onDelete(commentL1.id)}>
                          Delete
                        </span>
                      )}
                      {modalId === commentL1.id && (
                        <CommentDeletionModal
                          onFinish={() => handleDeleteComment1(commentL1.id)}
                          deleteModalVisible={deleteModalVisible}
                          cancelDelete={cancelDelete}
                        />
                      )}
                    </Space>
                  </div>
                  <div style={{ grid_row: 2 }}>
                    <Button
                      type="link"
                      onClick={() =>
                        toggleReplies(
                          commentL1.id,
                          expandStates[`${commentL1.id}`]
                        )
                      }
                    >
                      {expandStates[`${commentL1.id}`]
                        ? "Hide replies"
                        : "Show replies"}
                    </Button>
                    <span
                      onClick={() =>
                        toggleAddReply(
                          commentL1.id,
                          replyingStates[`${commentL1.id}`]
                        )
                      }
                    >
                      {replyingStates[`${commentL1.id}`] ? "Unreply" : "Reply"}
                    </span>
                  </div>
                </div>
                {typeof replyingStates[`${commentL1.id}`] !== "undefined" &&
                  replyingStates[`${commentL1.id}`] !== false && (
                    <Comment
                      author={
                        <span style={{ color: "black" }}>
                          <b>{localStorage.getItem("username")}</b>
                        </span>
                      }
                      avatar={
                        <Avatar
                          src="https://img.freepik.com/free-psd/3d-icon-social-media-app_23-2150049569.jpg?w=740&t=st=1694752925~exp=1694753525~hmac=9f7cafd64ea5660940fd27018077159cd1115668df4f6ac95e5127faec762e83"
                          alt="Han Solo"
                        />
                      }
                      content={
                        <Editor
                          onChange={handleChange}
                          onSubmit={() => handleSubmitReply(commentL1.id)}
                          submitting={loading2}
                          value={newCommentValue}
                        />
                      }
                    />
                  )}
                {typeof expandStates[`${commentL1.id}`] !== "undefined" &&
                  expandStates[`${commentL1.id}`] !== false && (
                    <div>
                      <List
                        loading={loading2}
                        dataSource={dataL2[`${commentL1.id}`]}
                        renderItem={(commentL2) =>
                          typeof commentL2 !== "undefined" && (
                            <div>
                              <Comment
                                author={
                                  <span
                                    style={{
                                      color: "black",
                                    }}
                                  >
                                    <b>{commentL2.user.username}</b>
                                  </span>
                                }
                                avatar={
                                  <Avatar
                                    src="https://img.freepik.com/free-psd/3d-icon-social-media-app_23-2150049569.jpg?w=740&t=st=1694752925~exp=1694753525~hmac=9f7cafd64ea5660940fd27018077159cd1115668df4f6ac95e5127faec762e83"
                                    alt="Han Solo"
                                  />
                                }
                                datetime={
                                  <span
                                    style={{
                                      color: "black",
                                    }}
                                  >
                                    {moment(commentL2.posted_time).fromNow()}
                                  </span>
                                }
                                content={commentL2.content}
                                actions={[
                                  <Space size={15}>
                                    <span
                                      onClick={() =>
                                        toggleLike(
                                          commentL2.id,
                                          thumbsupStates.has(commentL2.id)
                                        )
                                      }
                                    >
                                      {thumbsupStates.has(commentL2.id) ? (
                                        <LikeFilled />
                                      ) : (
                                        <LikeOutlined />
                                      )}{" "}
                                      {thumbsupCounts[`${commentL2.id}`]}
                                    </span>

                                    {commentL2.user.username ===
                                      localStorage.getItem("username") && (
                                      <span
                                        onClick={() => onDelete(commentL2.id)}
                                      >
                                        Delete
                                      </span>
                                    )}
                                    {modalId === commentL2.id && (
                                      <CommentDeletionModal
                                        onFinish={() =>
                                          handleDeleteComment2(
                                            commentL1.id,
                                            commentL2.id
                                          )
                                        }
                                        deleteModalVisible={deleteModalVisible}
                                        cancelDelete={cancelDelete}
                                      />
                                    )}
                                  </Space>,
                                ]}
                              ></Comment>
                            </div>
                          )
                        }
                      />
                    </div>
                  )}
              </span>,
            ]}
          ></Comment>
        )}
      />
    </>
  );
}

export default CommentPage;

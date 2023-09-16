import IssueListR from "./IssueListR";
import React, { useState, useEffect } from "react";
import { Tabs, notification } from "antd";
import { getUnPickedUpCount } from "../utils";
import MessagePage from "./MessagePage";
import CommentPage from "./CommentPage";

const { TabPane } = Tabs;

const ResidentHomePage = () => {
  const [unreadMessageCount, setUnreadMessageCount] = useState(null);

  const fetchUnreadMessageCount = async () => {
    try {
      const unreadCount = await getUnPickedUpCount(null);
      setUnreadMessageCount(unreadCount);
    } catch (error) {
      console.error("Error fetching unread message count:", error);
      notification.error({
        message: "Error",
        description: "There was an error fetching the unread message count.",
      });
    }
  };

  useEffect(() => {
    fetchUnreadMessageCount();
  }, []);

  return (
    <Tabs defaultActiveKey="1" destroyInactiveTabPane={true}>
      <TabPane tab="Forum" key="1">
        <CommentPage />
      </TabPane>
      <TabPane tab="My Requests" key="2">
        <IssueListR />
      </TabPane>
      <TabPane
        tab={
          <span>
            Packages
            {unreadMessageCount !== null && (
              <span style={{ marginLeft: "8px", fontWeight: "bold" }}>
                ({unreadMessageCount})
              </span>
            )}
          </span>
        }
        key="3"
      >
        <MessagePage />
      </TabPane>
    </Tabs>
  );
};

export default ResidentHomePage;

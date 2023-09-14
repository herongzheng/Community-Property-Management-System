import IssueListR from "./IssueListR";
import React, { useState, useEffect } from "react";
import { Tabs, notification, List, Avatar, message } from "antd";
import { getUnreadMessageCount } from "../utils";
import MessagePage from "./MessagePage";
import ChatPage from "./ChatPage";

const { TabPane } = Tabs;

// function ResidentHomePage() {
//   return (
//     <Tabs defaultActiveKey="1" destroyInactiveTabPane={true}>
//       <TabPane tab="Forum" key="1">
//         <div>chat component</div>
//       </TabPane>
//       <TabPane tab="My Requests" key="2">
//         <IssueListR />
//       </TabPane>
//       <TabPane tab="Message" key="3">
//         <div>message component</div>
//       </TabPane>
//     </Tabs>
//   );
// }

const ResidentHomePage = () => {
  const [unreadMessageCount, setUnreadMessageCount] = useState(null);

  const fetchUnreadMessageCount = async () => {
    try {
      const unreadCount = await getUnreadMessageCount();
      setUnreadMessageCount(unreadCount);
    } catch (error) {
      console.error("Error fetching unread message count:", error);
      notification.error({
        message: "Error",
        description: "There was an error fetching the unread message count.",
      });
    }
  };

  console.log(unreadMessageCount);

  useEffect(() => {
    fetchUnreadMessageCount();
  }, []);

  return (
    <Tabs defaultActiveKey="1" destroyInactiveTabPane={true}>
      <TabPane tab="Forum" key="1">
        <ChatPage />
      </TabPane>
      <TabPane tab="My Requests" key="2">
        <IssueListR />
      </TabPane>
      <TabPane
        tab={
          <span>
            Message Page
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

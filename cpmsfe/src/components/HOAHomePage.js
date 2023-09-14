import React from "react";
import { Tabs } from "antd";
import IssueListH from "./IssueListH";
import Residents from "./Residents";
import ChatPage from "./ChatPage";
import PackagesPage from "./PackagesPage";
import ResidentManagementPage from "./ResidentManagementPage";

const { TabPane } = Tabs;

function HOAHomePage() {
  return (
    <Tabs defaultActiveKey="1" destroyInactiveTabPane={true}>
      <TabPane tab="Forum" key="1">
        <ChatPage />
      </TabPane>
      <TabPane tab="Request Management" key="2">
        <IssueListH />
      </TabPane>
      <TabPane tab="Resident Management" key="3">
        <ResidentManagementPage />
      </TabPane>
      <TabPane tab="Package Management" key="4">
        <PackagesPage />
      </TabPane>
    </Tabs>
  );
}

export default HOAHomePage;

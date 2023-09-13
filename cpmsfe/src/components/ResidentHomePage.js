import React from "react";
import { Tabs } from "antd";
import IssueListR from "./IssueListR";

const { TabPane } = Tabs;

function ResidentHomePage() {
  return (
    <Tabs defaultActiveKey="1" destroyInactiveTabPane={true}>
      <TabPane tab="Forum" key="1">
        <div>chat component</div>
      </TabPane>
      <TabPane tab="My Requests" key="2">
        <IssueListR />
      </TabPane>
      <TabPane tab="Message" key="3">
        <div>message component</div>
      </TabPane>
    </Tabs>
  );
}

export default ResidentHomePage;

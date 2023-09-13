import React from "react";
import { Tabs } from "antd";
import IssueListH from "./IssueListH";

const { TabPane } = Tabs;

function HOAHomePage() {
  return (
    <Tabs defaultActiveKey="1" destroyInactiveTabPane={true}>
      <TabPane tab="Resident Management" key="1">
        <div>Resident Management component</div>
      </TabPane>
      <TabPane tab="Request Management" key="2">
        <IssueListH />
      </TabPane>
      <TabPane tab="Forum" key="3">
        <div>chat component</div>
      </TabPane>
      <TabPane tab="Package Management" key="4">
        <div>Package component</div>
      </TabPane>
    </Tabs>
  );
}

export default HOAHomePage;

import React, { useState, useEffect } from "react";
import { Layout } from "antd";
import "./App.css";
import ResidentHomePage from "./component/ResidentHomePage";
import HOAHomePage from "./component/HOAHomePage";

const { Header, Content } = Layout;

function App() {
  const [authed, setAuthed] = useState(false);
  const [asHost, setAsHost] = useState(false);
  const [username, setUsername] = useState(null);

  const renderContent = () => {
    // if (!this.state.authed) {
    //   return <div>login page</div>;
    // }

    // resident Room003
    // const token =
    //   "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSb29tMDAzIiwiZXhwIjoxNjk0ODA0OTA4LCJpYXQiOjE2OTQ2MzIxMDh9.tW3jY_mdGGzqQ0u4Gk4iLDs_2Vfnn3QSCiSuuSPNJZQ";

    // Room002
    // const token =
    //   "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSb29tMDAyIiwiZXhwIjoxNjk0ODA0OTc1LCJpYXQiOjE2OTQ2MzIxNzV9.OEDiQdFMswHZOoy1gW6c8eCPz13tpBTXwJQh6NYOvgw";
    // HOA
    // const token =
    //   "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcmFkIiwiZXhwIjoxNjk0Nzk5NDczLCJpYXQiOjE2OTQ2MjY2NzN9.p6_3zfyQA1qgXIOfsGHu0yCsV9fMHthfdR0M7VElRSI";
    // localStorage.setItem("authToken", token);

    if (asHost) {
      return <HOAHomePage />;
    }

    return <ResidentHomePage />;
  };

  return (
    <Layout>
      <Header>
        <div style={{ fontSize: 16, fontWeight: 600, color: "white" }}>
          Community Property Management System
        </div>
      </Header>
      <Content
        style={{ height: "calc(100% - 64px)", margin: 20, overflow: "auto" }}
      >
        {renderContent()}
      </Content>
    </Layout>
  );
}

export default App;

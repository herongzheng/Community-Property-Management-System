import React, { useState, useEffect } from "react";
import { Layout, Dropdown, Menu, Button } from "antd";
import "./App.css";
import ResidentHomePage from "./components/ResidentHomePage";
import HOAHomePage from "./components/HOAHomePage";
import { UserOutlined } from "@ant-design/icons";
import LoginPage from "./components/LoginPage";
import ChangePassword from "./components/ChangePassword";

const { Header, Content } = Layout;

class App extends React.Component {
  state = {
    authed: false,
    asHost: false,
    isChangingPassword: false,
    clickChangePassword: false,
  };

  componentDidMount() {
    const authToken = localStorage.getItem("authToken");
    const asHost = localStorage.getItem("asHost") === "true";
    this.setState({
      authed: authToken !== null,
      asHost,
    });
  }

  handleLoginSuccess = (token, username, asHost) => {
    localStorage.setItem("authToken", token);
    localStorage.setItem("asHost", asHost);
    localStorage.setItem("username", username);
    this.setState({
      authed: true,
      asHost,
      clickChangePassword: false,
    });
  };
  handleLogOut = () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("asHost");
    this.setState({
      authed: false,
      isChangingPassword: false,
      clickChangePassword: false, // you need to add this line for every function to confirm the stete reset
    });
  };

  handleChangePassword = () => {
    this.setState({
      isChangingPassword: true,
    });
  };

  handleClickChangePassword = () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("asHost");
    this.setState({
      authed: false,
      authToken: "",
      asHost: false,
      clickChangePassword: true,
      isChangingPassword: false,
    });
  };
  handleCancel = () => {
    this.setState({
      authed: true,
      isChangingPassword: false,
    });
  };
  renderContent = () => {
    if (!this.state.authed || this.state.clickChangePassword) {
      return <LoginPage handleLoginSuccess={this.handleLoginSuccess} />;
    }
    if (this.state.authed && this.state.isChangingPassword) {
      return (
        <ChangePassword
          handleClickChangePassword={this.handleClickChangePassword.bind(this)}
          handleCancel={this.handleCancel}
          asHost={this.state.asHost}
        />
      );
    }

    if (this.state.asHost) {
      return <HOAHomePage />;
    }
    return <ResidentHomePage />;
  };

  userMenu = (
    <Menu>
      <Menu.Item key="logout" onClick={this.handleLogOut}>
        Log Out
      </Menu.Item>
      <Menu.Item key="changepassword" onClick={this.handleChangePassword}>
        Change Password
      </Menu.Item>
    </Menu>
  );

  render() {
    return (
      <Layout style={{ height: "100vh" }}>
        <Header style={{ display: "flex", justifyContent: "space-between" }}>
          <div style={{ fontSize: 16, fontWeight: 600, color: "white" }}>
            Sweet Home Community
          </div>
          {this.state.authed && (
            <div>
              <Dropdown trigger="click" overlay={this.userMenu}>
                <Button icon={<UserOutlined />} shape="circle" />
              </Dropdown>
            </div>
          )}
        </Header>
        <Content
          style={{ height: "calc(100% - 64px)", margin: 20, overflow: "auto" }}
        >
          {this.renderContent()}
        </Content>
      </Layout>
    );
  }
}

export default App;

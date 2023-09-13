import React from "react";
import { Form, Input, Space, Checkbox, Button, message } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { login } from "../utils"; // Adjust the path to your utils file if it's different.

class LoginPage extends React.Component {
  formRef = React.createRef();
  state = {
    asHost: false,
    loading: false,
  };

  handleLogin = async () => {
    const formInstance = this.formRef.current;

    try {
      await formInstance.validateFields();
    } catch (error) {
      return;
    }

    this.setState({
      loading: true,
    });

    try {
      const { asHost } = this.state;
      const resp = await login(formInstance.getFieldsValue(true), asHost);
      const username = formInstance.getFieldValue("username");
      this.props.handleLoginSuccess(resp.token, username, asHost); // Ensure your parent component handles this.
    } catch (error) {
      message.error(error.message);
    } finally {
      this.setState({
        loading: false,
      });
    }
  };

  handleCheckboxOnChange = (e) => {
    this.setState({
      asHost: e.target.checked,
    });
  };

  render() {
    return (
      <div style={{ width: 500, margin: "50px auto" }}>
        <Form ref={this.formRef}>
          <Form.Item
            name="username"
            rules={[
              {
                required: true,
                message: "Please input your Username!",
              },
            ]}
          >
            <Input
              disabled={this.state.loading}
              prefix={<UserOutlined className="site-form-item-icon" />}
              placeholder="Username"
            />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: "Please input your Password!",
              },
            ]}
          >
            <Input.Password
              disabled={this.state.loading}
              placeholder="Password"
            />
          </Form.Item>
        </Form>
        <Space>
          <Checkbox
            disabled={this.state.loading}
            checked={this.state.asHost}
            onChange={this.handleCheckboxOnChange}
          >
            HOA
          </Checkbox>
          <Button
            onClick={this.handleLogin}
            disabled={this.state.loading}
            shape="round"
            type="primary"
          >
            Log in
          </Button>
        </Space>
      </div>
    );
  }
}

export default LoginPage;

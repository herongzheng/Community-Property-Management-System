import React, { useState } from "react";
import { Form, Input, Button, message, Typography } from "antd";
import { changePassword } from "../utils";

const ChangePasswordPage = (props) => {
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();

  const onFinish = async (values) => {
    setLoading(true);

    try {
      const data = await changePassword(values);

      if (!data) {
        message.success("Password changed successfully. Please login again.");
        props.handleClickChangePassword();
      } else {
        message.error(
          "Failed to change password. Please check your old password and try again."
        );
        form.setFields([
          {
            name: "old_password",
            errors: ["Incorrect old password!"],
          },
        ]);
      }
    } catch (error) {
      message.error(error.message || "An error occurred. Please try again.");
    } finally {
      setLoading(false);
    }
  };
  return (
    <div style={{ width: 500, margin: "20px auto" }}>
      <Typography.Title level={2}>Change Password</Typography.Title>
      <Form form={form} onFinish={onFinish}>
        <Form.Item
          name="username"
          label={props.asHost ? "User name" : "Room Number"}
        >
          <Input placeholder={localStorage.getItem("username")} disabled />
        </Form.Item>

        <Form.Item
          name="old_password"
          rules={[
            { required: true, message: "Please input your old password!" },
          ]}
          label="Old Password"
        >
          <Input.Password placeholder="Old Password" />
        </Form.Item>

        <Form.Item
          name="new_password"
          rules={[
            { required: true, message: "Please input your new password!" },
          ]}
          label="New Password"
        >
          <Input.Password placeholder="New Password" />
        </Form.Item>

        <Form.Item
          name="confirmPassword"
          dependencies={["new_password"]}
          rules={[
            { required: true, message: "Please confirm your new password!" },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue("new_password") === value) {
                  return Promise.resolve();
                }
                return Promise.reject(new Error("Passwords do not match!"));
              },
            }),
          ]}
          label="Confirm New Password"
        >
          <Input.Password placeholder="Confirm Password" />
        </Form.Item>

        <Form.Item
          name="first_name"
          rules={[{ required: true, message: "Please input your First Name!" }]}
          label="First Name"
        >
          <Input placeholder="First Name" />
        </Form.Item>

        <Form.Item
          name="last_name"
          rules={[{ required: true, message: "Please input your Last Name!" }]}
          label="Last Name"
        >
          <Input placeholder="Last Name" />
        </Form.Item>

        <Form.Item name="email" label="Email Address">
          <Input placeholder="Email Address" />
        </Form.Item>

        <Form.Item name="phone_number" label="Phone Number">
          <Input placeholder="Phone Number" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            Change Password
          </Button>
          <Button
            type="default"
            onClick={props.handleCancel}
            style={{ marginLeft: "200px" }}
          >
            Cancel
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default ChangePasswordPage;

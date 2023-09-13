import React from "react";
import { Form, Input, InputNumber, Button, message, Space } from "antd";
import { createIssue } from "../utils";

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

const tailLayout = {
  wrapperCol: {
    offset: 8,
    span: 16,
  },
};

class SubmitRequest extends React.Component {
  state = {
    loading: false,
  };

  fileInputRef = React.createRef();

  handleSubmit = async (values) => {
    const formData = new FormData();
    const { files } = this.fileInputRef.current;

    if (files.length > 5) {
      message.error("You can at most upload 5 pictures.");
      return;
    }

    for (let i = 0; i < files.length; i++) {
      formData.append("images", files[i]);
    }

    formData.append("title", values.title);
    formData.append("content", values.content);

    this.setState({
      loading: true,
    });

    try {
      await createIssue(formData);
      message.success("submit successfully");
    } catch (error) {
      message.error(error.message);
    } finally {
      this.setState({
        loading: false,
      });
      this.props.onSubmit();
    }
  };

  render() {
    return (
      <Form
        {...layout}
        name="nest-messages"
        onFinish={this.handleSubmit}
        style={{ maxWidth: 1000, margin: "auto" }}
      >
        <Form.Item name="title" label="Title" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item name="content" label="Content" rules={[{ required: true }]}>
          <Input.TextArea autoSize={{ minRows: 5, maxRows: 10 }} />
        </Form.Item>
        <Form.Item name="picture" label="Picture" rules={[{ required: false }]}>
          <input
            type="file"
            accept="image/png, image/jpeg"
            ref={this.fileInputRef}
            multiple={true}
          />
        </Form.Item>

        <Form.Item {...tailLayout}>
          <Space size={20}>
            <Button
              type="primary"
              htmlType="submit"
              loading={this.state.loading}
              onClick={this.props.onSubmit}
            >
              Submit
            </Button>
            <Button htmlType="button" onClick={this.props.onCancel}>
              Cancel
            </Button>
          </Space>
        </Form.Item>
      </Form>
    );
  }
}

export default SubmitRequest;

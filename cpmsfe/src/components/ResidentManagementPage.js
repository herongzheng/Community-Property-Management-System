import { ProList } from "@ant-design/pro-components";
import { useState, useEffect } from "react";
import { getResidents, createResident } from "../utils";
import { message, Space, Button, Modal, Form } from "antd";

function ResidentManagementPage() {
  const [data, SetData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [displayModal, setDisplayModal] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    getResidents()
      .then((data) => {
        SetData(data);
      })
      .catch((err) => {
        message.error(err.message);
      });
    setLoading(false);
  }, [loading]);

  const handleNewResident = async (username) => {
    try {
      await createResident(username);
      message.success("Renew the user successfully");
      setDisplayModal(false);
    } catch (error) {
      message.error(error.message);
    } finally {
      setLoading(true);
    }
  };

  const newResidentOnClick = (username) => {
    setCurrentUser(username);
    setDisplayModal(true);
  };

  const handleCancel = () => {
    setDisplayModal(false);
  };

  return (
    <>
      <ProList
        locale={{
          emptyText: "No residents to display ...",
        }}
        loading={loading}
        itemLayout="vertical"
        rowKey="id"
        dataSource={data}
        metas={{
          title: { dataIndex: "username" },
          subTitle: {
            dataIndex: "apt_number",
            render: (item, row) => {
              return (
                <Space size={5}>
                  <span>
                    {row.last_name ? `${row.last_name}, ${row.first_name}` : ""}
                  </span>
                  <span>
                    {row.phone_number ? ` ❤️ Phone: ${row.phone_number}` : ""}
                  </span>
                  <span>{row.email ? ` ❤️ Email: ${row.email}` : ""}</span>
                </Space>
              );
            },
          },

          extra: {
            dataIndex: "username",
            render: (item) => {
              return (
                <Button
                  shape="round"
                  onClick={() => newResidentOnClick(`${item}`)}
                >
                  New Resident
                </Button>
              );
            },
          },
        }}
      />
      <Modal
        title="New resident"
        visible={displayModal}
        onCancel={handleCancel}
        footer={null}
        //   destroyOnClose={true}
      >
        <Form
          name="confirm to renew a room"
          onFinish={() => handleNewResident(currentUser)}
          preserve={false}
        >
          <p>New username: your room number</p>
          <p>Temporary password: 123456</p>
          <h3>Do you want to remove the previous resident's information?</h3>

          <br />
          <Form.Item>
            <Button
              type="primary"
              onClick={handleCancel}
              style={{ marginLeft: "100px" }}
            >
              No
            </Button>
            <Button
              type="default"
              htmlType="submit"
              style={{ marginLeft: "160px" }}
            >
              Yes
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
}

export default ResidentManagementPage;

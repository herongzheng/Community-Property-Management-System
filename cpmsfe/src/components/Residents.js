import React, { useState, useEffect } from "react";
import "antd/dist/antd.css";
import {
  Divider,
  List,
  Typography,
  Button,
  Popover,
  message,
  Modal,
} from "antd";
import { getResidents, createResident } from "../utils";

const Residents = () => {
  const [data, setData] = useState([]);

  const [modalText, setModalText] = useState("Content of the modal");
  const [isModalOpen, setIsModalOpen] = useState(false);
  useEffect(() => {
    getResidents()
      .then((residents) => setData(residents))
      .catch((error) => message.error(error.message));
  }, []);

  const handleNewResident = (username) => {
    setModalText(username);
    setIsModalOpen(true);
  };

  const content = (resident) => {
    console.log(resident);
    return (
      <div>
        <p>
          Name: {resident.firstName} {resident.lastName}
        </p>
        <p>Phone: {resident.phone_number}</p>
        <p>Email: {resident.email}</p>
      </div>
    );
  };

  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = async () => {
    setIsModalOpen(false);
    await createResident(modalText)
      .then(() => {
        message.success("New resident created successfully!");
      })
      .catch((error) => message.error(error.message));
    await getResidents()
      .then((residents) => setData(residents))
      .catch((error) => message.error(error.message));
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <Divider orientation="left">Resident</Divider>
      <Modal
        title="New Resident"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <p>{modalText}</p>
        <p>Temporary Password: 123456</p>
      </Modal>
      <List
        header={<div></div>}
        footer={<div></div>}
        bordered
        dataSource={data}
        renderItem={(resident) => (
          <List.Item
            actions={[
              <Button onClick={() => handleNewResident(resident.username)}>
                New Resident
              </Button>,
            ]}
          >
            <div>
              <Popover content={content(resident)} title="Resident Info">
                <Typography.Text mark>{resident.username}</Typography.Text>
              </Popover>
            </div>
          </List.Item>
        )}
      />
    </>
  );
};

export default Residents;

import React, { useState, useEffect } from "react";
import { Button, Modal, Input, List, Avatar, message, Form } from "antd";
import { createPackages, packages, pickupPackage } from "../utils"; // Assuming you have service file for API calls

const PackagesPage = () => {
  const [data, setData] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [inputData, setInputData] = useState({
    resident: { username: "" },
    description: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
      setLoading(true);
      const response = await packages();
      setData(response);
    } catch (error) {
      console.error("Error fetching package data:", error);
      message.error(error.message || "Error fetching package data");
      setError(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handlePickup = async (item) => {
    try {
      setLoading(true);
      await pickupPackage(item.id);
      message.success(`Package picked up`);
      fetchData();
    } catch (error) {
      console.error(error);
      message.error(error.message || "Failed to pickup package");
      setError(error);
    } finally {
      setLoading(false);
    }
  };

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = async () => {
    console.log(inputData.resident.username);
    // if (!inputData.resident.username || !inputData.description) {
    //   message.error("Both username and description are required.");
    //   return;
    // }

    try {
      setLoading(true);
      const packageData = {
        username: inputData.resident.username,
        description: inputData.description,
      };
      await createPackages(packageData);
      setIsModalVisible(false);
      setInputData({ resident: { username: "" }, description: "" });
      message.success("Package created successfully");
      fetchData();
    } catch (error) {
      console.error(error);
      message.error(error.message || "Failed to create package");
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const handleInputChange = (e, key) => {
    if (key === "username") {
      setInputData((prevState) => ({
        ...prevState,
        resident: { ...prevState.resident, username: e.target.value },
      }));
    } else {
      setInputData((prevState) => ({
        ...prevState,
        [key]: e.target.value,
      }));
    }
  };

  return (
    <div>
      <Button
        type="primary"
        onClick={showModal}
        style={{
          position: "fixed",
          bottom: "10px",
          left: "10px",
          zIndex: 1000,
        }}
      >
        Create Package
      </Button>

      <Modal
        title="New package"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        confirmLoading={loading}
      >
        <Form>
          <Form.Item
            name="Room number"
            label="Room number"
            rules={[
              {
                required: true,
                message: "Please input room number",
              },
            ]}
          >
            <Input
              rules={[{ require: true }]}
              placeholder="Input the resident room number"
              value={inputData.resident.username}
              onChange={(e) => handleInputChange(e, "username")}
              style={{ marginBottom: "10px" }}
            />
          </Form.Item>
          <Form.Item
            name="Description"
            label="Pack Description"
            rules={[
              {
                required: false,
                message: "Please input package description",
              },
            ]}
          >
            <Input
              placeholder="Input Description"
              value={inputData.description}
              onChange={(e) => handleInputChange(e, "description")}
              style={{ marginBottom: "10px" }}
            />
          </Form.Item>
        </Form>
      </Modal>

      <List
        itemLayout="horizontal"
        dataSource={data}
        loading={loading}
        renderItem={(item, index) => (
          <List.Item
            key={item.id || index}
            actions={[
              !item.pickup_date && (
                <Button
                  key={item.id || index}
                  type="primary"
                  onClick={() => handlePickup(item)}
                >
                  Pickup
                </Button>
              ),
            ]}
          >
            <List.Item.Meta
              avatar={<Avatar src={item.avatar} />}
              title={<a href="#">{item.resident.username || "Unknown User"}</a>}
              description={
                <>
                  <div>
                    Description:{" "}
                    {item.description || "No description available"}
                  </div>
                  <div>
                    Received Date:{" "}
                    {item.received_date
                      ? new Date(item.received_date).toLocaleDateString()
                      : "N/A"}
                  </div>
                  <div>
                    Pickup Date:{" "}
                    {item.pickup_date
                      ? new Date(item.pickup_date).toLocaleDateString()
                      : "N/A"}
                  </div>
                </>
              }
            />
          </List.Item>
        )}
      />
    </div>
  );
};

export default PackagesPage;

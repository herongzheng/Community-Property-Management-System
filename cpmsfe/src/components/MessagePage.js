import { List, Avatar, message } from "antd";
import { messages } from "../utils";
import React, { useState, useEffect } from "react";

const Loading = () => <p>Loading...</p>;
const Error = () => <p>Error loading data. Please try again later.</p>;

const ListItem = ({ item, index }) => (
  <List.Item>
    <List.Item.Meta
      // avatar={<Avatar src={item.avatar} />}
      title={<a href={item.url || "#"}>{item.title}</a>}
      description={
        <>
          <p>
            <b>{`Your package has arrived in our office. Please pick it up. Description: ${item.description}`}</b>
          </p>
          <p>
            {`Received date : ${new Date(
              item.received_date
            ).toLocaleDateString()}, `}{" "}
            {item.pickup_date
              ? `Picked up date : ${new Date(
                  item.pickup_date
                ).toLocaleDateString()} `
              : "Not Picked Up Yet"}{" "}
          </p>
        </>
      }
    />
  </List.Item>
);

const MessagePage = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
      setLoading(true);
      const responseData = await messages();
      setData(responseData);
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

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <Error />;
  }

  return (
    <List
      itemLayout="horizontal"
      dataSource={data}
      renderItem={(item, index) => <ListItem item={item} index={index} />}
    />
  );
};

export default MessagePage;

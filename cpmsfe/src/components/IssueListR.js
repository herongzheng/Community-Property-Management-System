import { LikeOutlined, MessageOutlined, StarOutlined } from "@ant-design/icons";
import { ProList } from "@ant-design/pro-components";
import {
  Button,
  Tag,
  Space,
  Carousel,
  Image,
  Card,
  Modal,
  message,
} from "antd";
import { useState, useEffect } from "react";
import { ReactText } from "react";
import { LeftCircleFilled, RightCircleFilled } from "@ant-design/icons";
import React from "react";
import SubmitRequest from "./SubmitRequest";
import { getIssues } from "../utils";

class NewRquestButton extends React.Component {
  state = {
    modalVisible: false,
  };

  openModal = () => {
    this.setState({
      modalVisible: true,
    });
  };

  handleCancel = () => {
    this.setState({
      modalVisible: false,
    });
  };

  render() {
    const { modalVisible } = this.state;

    const modalTitle = `Request Submission Form`;

    return (
      <>
        <Button onClick={this.openModal} shape="round">
          New Request
        </Button>
        {modalVisible && (
          <Modal
            title={modalTitle}
            centered={true}
            visible={modalVisible}
            closable={false}
            footer={null}
            onCancel={this.handleCancel}
            destroyOnClose={true}
          >
            <SubmitRequest
              onSubmit={this.props.onSubmit}
              onCancel={this.handleCancel}
            />
          </Modal>
        )}
      </>
    );
  }
}

function IssueListR() {
  const [data, SetData] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getIssues()
      .then((data) => {
        SetData(data);
      })
      .catch((err) => {
        message.error(err.message);
      });
    setLoading(false);
  }, [loading]);

  const [expandedRowKeys, setExpandedRowKeys] = useState([]);

  return (
    <ProList
      locale={{
        emptyText: "No data to display, you can create a new request ...",
      }}
      loading={loading}
      toolBarRender={() => {
        return [
          <NewRquestButton onSubmit={() => setLoading(true)}>
            New Request
          </NewRquestButton>,
          <Button type="primary" shape="round" onClick={() => setLoading(true)}>
            Refresh
          </Button>,
        ];
      }}
      itemLayout="vertical"
      rowKey="id"
      //   headerTitle="Room "
      dataSource={data}
      expandable={{ expandedRowKeys, onExpandedRowsChange: setExpandedRowKeys }}
      metas={{
        title: { dataIndex: "title" },
        subTitle: {
          dataIndex: "report_date",
          render: (item, row) => {
            return (
              <Space size={5}>
                <span>{item}</span>
                <span>-</span>
                <span>{row.closed_date}</span>
              </Space>
            );
          },
        },
        description: {
          dataIndex: "content",
          render: (item) => <span>{item}</span>,
        },

        // actions: {
        //   render: (text, row) => {
        //     console.log(row);
        //     return row.name;
        //   },
        // },
        extra: {
          dataIndex: "resident",
          render: (item, row) => {
            if (!row.confirmed) {
              return (
                <Space size={15}>
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                      paddingRight: "1px",
                      paddingLeft: "5px",
                    }}
                  >
                    Status:
                  </Button>
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                    }}
                  >
                    Submitted
                  </Button>
                </Space>
              );
            }

            if (row.confirmed && row.closed_date === undefined) {
              return (
                <Space size={15}>
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                      paddingRight: "1px",
                      paddingLeft: "5px",
                    }}
                  >
                    Status:
                  </Button>
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                    }}
                  >
                    Confirmed
                  </Button>
                </Space>
              );
            }

            if (row.confirmed && row.closed_date != undefined) {
              return (
                <Space size={15}>
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                      paddingRight: "13px",
                      paddingLeft: "5px",
                    }}
                  >
                    Status:
                  </Button>
                  <Button
                    shape="round"
                    type="text"
                    style={{ fontWeight: "bold", paddingRight: "30px" }}
                  >
                    Closed
                  </Button>
                </Space>
              );
            }

            //   <Space size={5}>
            //     {item}

            //     {<Button color="blue">Confirm</Button>}
            //     <Button color="#5BD8A6">Close</Button>
            //   </Space>
          },
        },
        content: {
          dataIndex: "images",
          render: (item) => {
            if (item.length != 0) {
              return (
                <Card
                  style={{
                    width: 300,
                  }}
                >
                  <Carousel
                    dots={false}
                    arrows={true}
                    prevArrow={<LeftCircleFilled />}
                    nextArrow={<RightCircleFilled />}
                  >
                    {item.map((image, index) => (
                      <div key={index}>
                        <Image src={image.url} />
                      </div>
                    ))}
                  </Carousel>
                </Card>
              );
            }
          },
        },
      }}
    />
  );
}

export default IssueListR;

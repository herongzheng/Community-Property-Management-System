import { LikeOutlined, MessageOutlined, StarOutlined } from "@ant-design/icons";
import { ProList } from "@ant-design/pro-components";
import { Button, Tag, Space, Carousel, Image, Card, message } from "antd";
import { useState, useEffect } from "react";
import { LeftCircleFilled, RightCircleFilled } from "@ant-design/icons";
import { getIssues, confirmIssue, closeIssue } from "../utils";

function IssueListH() {
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
  // const dataSource = [
  //   {
  //     id: 1,
  //     title: "Leak in master bathroom",
  //     content: "123434 234 ,234234235,234",
  //     report_date: "2023-08-05",
  //     closed_date: "2023-08-06",
  //     confirmed: true,
  //     tenant_id: "Room003",
  //     images: [
  //       {
  //         url: "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png",
  //         issue: 1,
  //       },
  //       {
  //         url: "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png",
  //         issue: 1,
  //       },
  //     ],
  //   },
  //   {
  //     id: 2,
  //     title: "Ant Design",
  //     content: "There is no problem with Ant Design",
  //     report_date: "2023-08-06",
  //     closed_date: null,
  //     confirmed: false,
  //     tenant_id: "Room005",
  //     images: [
  //       {
  //         url: "https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png",
  //         issue: 2,
  //       },
  //     ],
  //   },
  //   {
  //     id: 3,
  //     title: "Air Conditioner Problem",
  //     content: "123434 234 ,234234235,234235",
  //     report_date: "2023-08-07",
  //     closed_date: null,
  //     confirmed: true,
  //     tenant_id: "Room001",
  //     images: [],
  //   },
  //   {
  //     id: 4,
  //     title: "Request Pest Control",
  //     content: "123434 234 ,234234235,234235",
  //     report_date: "2023-08-07",
  //     closed_date: "2023-08-09",
  //     confirmed: true,
  //     tenant_id: "Room002",
  //     images: [],
  //   },
  // ];

  const [expandedRowKeys, setExpandedRowKeys] = useState([]);

  const handleConfirm = async (issueId) => {
    try {
      await confirmIssue(issueId);
      message.success("confirm the issue successfully");
    } catch (error) {
      message.error(error.message);
    } finally {
      setLoading(true);
    }
  };

  const handleClose = async (issueId) => {
    try {
      await closeIssue(issueId);
      message.success("close the issue successfully");
    } catch (error) {
      message.error(error.message);
    } finally {
      setLoading(true);
    }
  };

  return (
    <ProList
      locale={{ emptyText: "Looks like there is no request ..." }}
      loading={loading}
      toolBarRender={() => {
        return [
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
                  {item.username}
                  <Button
                    shape="round"
                    loading={loading}
                    onClick={() => handleConfirm(row.id)}
                  >
                    Confirm
                  </Button>
                  <Button shape="round" disabled={true}>
                    Close
                  </Button>
                </Space>
              );
            }

            if (row.confirmed && row.closed_date === undefined) {
              return (
                <Space size={15}>
                  {item.username}
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                      paddingRight: "5px",
                      paddingLeft: "7px",
                    }}
                  >
                    Confirmed
                  </Button>
                  <Button
                    shape="round"
                    loading={loading}
                    onClick={() => handleClose(row.id)}
                  >
                    Close
                  </Button>
                </Space>
              );
            }

            if (row.confirmed && row.closed_date != undefined) {
              return (
                <Space size={15}>
                  {item.username}
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                      paddingRight: "0px",
                      paddingLeft: "7px",
                    }}
                  >
                    Confirmed
                  </Button>
                  <Button
                    shape="round"
                    type="text"
                    style={{
                      fontWeight: "bold",
                      paddingRight: "9px",
                      paddingLeft: "18px",
                    }}
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

export default IssueListH;

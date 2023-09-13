INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(1, "2023-08-22 23:28:08", "This is the first post", 1, "Ashley", null, 0);
INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(2, "2023-08-23", "second post reply to post 1", 0, "Room002", 1, 1);
INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(3, "2023-08-24", "third post", 0, "Room001", null, 2);
INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(4, "2023-08-25 12:21:23", "good 4 you", 0, "Room003", null, 0);

INSERT INTO issue(id, title, content, report_date, closed_date, confirmed, tenant_id)
values(1, "air conditioner doesn't work", "1234556456", "2023-08-22", null , true, "Room003");

INSERT INTO issue_image(url, issue_id) values("https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png", 1);
INSERT INTO issue_image(url, issue_id) values("https://thumbs.dreamstime.com/z/golden-retriever-dog-21668976.jpg?w=992", 1);

INSERT INTO issue(id, title, content, report_date, closed_date, confirmed, tenant_id)
values(2, "ant design is good", "the request is this", "2023-08-24", null , false, "Room003");

INSERT INTO issue(id, title, content, report_date, closed_date, confirmed, tenant_id)
values(3, "No thing happend", "A good day!", "2023-08-25", "2023-08-28", true, "Room003");

INSERT INTO issue(id, title, content, report_date, closed_date, confirmed, tenant_id)
values(4, "lightbulb broken in dining room", "this is created by Room002", "2023-08-27", null, false, "Room002");

INSERT INTO issue(id, title, content, report_date, closed_date, confirmed, tenant_id)
values(5, "leak in master bedroom", "the position is ...", "2023-08-29", null , true, "Room001");
INSERT INTO issue_image(url, issue_id) values("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQrPlpEAEsOStyyv5gsbH83mdKKwmp2WhR_A&usqp=CAU", 5);



INSERT INTO package(id, received_date, pickup_date, is_read, tenant_id)
values(5, "2023-08-23", null, false, "Room003")

INSERT INTO package(id, received_date, pickup_date, is_read, tenant_id)
values(2, "2023-08-21", "2023-08-22", true, "Room003")
INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(1, "2023-08-22 23:28:08", "This is the first post", 1, "Ashley", null, 0);
INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(2, "2023-08-23", "second post reply to post 1", 0, "Room002", 1, 1);
INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(3, "2023-08-24", "third post", 0, "Room001", null, 2);
INSERT INTO post(id, posted_time, content, is_important, user_id, reply_to, likes)
values(4, "2023-08-25 12:21:23", "good 4 you", 0, "Room003", null, 0);
# SELECT * from post order by is_important DESC, id DESC;
# SELECT * FROM user;

INSERT INTO issue(id, report_date, closed_date, confirmed, tenant_id)
values(1, "2023-08-22", null , true, "Room003");

INSERT INTO issue_image(url, issue_id) values("this is url", 1);
INSERT INTO issue_image(url, issue_id) values("this is url 2", 1);

INSERT INTO issue(id, report_date, closed_date, confirmed, tenant_id)
values(2, "2023-08-26", null , false, "Room001");

INSERT INTO package(id, received_date, pickup_date, is_read, tenant_id)
values(5, "2023-08-23", null, false, "Room003")

INSERT INTO package(id, received_date, pickup_date, is_read, tenant_id)
values(2, "2023-08-21", "2023-08-22", true, "Room003")

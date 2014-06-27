# --- !Ups

insert into s3image (id, created, key, bucket) values (1, '2014-04-01', '09f524c2-3dc5-4eba-924d-9d6b12bae297.jpg', 'play.bookstore');
insert into s3image (id, created, key, bucket) values (2, '2014-03-01', '132baa3b-f863-47b1-952d-c69b290e9072.jpg', 'play.bookstore');
insert into s3image (id, created, key, bucket) values (3, '2012-12-01', 'ac90a117-9916-4cc9-a30a-9dcc0afba066.jpg', 'play.bookstore');
insert into s3image (id, created, key, bucket) values (4, '2013-03-01', '22310261-3444-4872-85b6-086fc58c9fe9.jpg', 'play.bookstore');

insert into book (id, created, title, description, price, image_id) values (1, '2014-04-01','OpenShift Primer - Revision 2', 'Are you tired of requesting a new development machine for your application? Are you sick of having to set up a new test environment for your application? Do you just want to focus on developing your application in peace without "dorking with the stack" all of the time? We hear you. We have been there too. Have no fear, OpenShift is here! This book is all about getting you started, hands-on, with Red Hat OpenShift PaaS. With new content and information on the latest updates, this revised edition is developer focused, concentrating on getting you working on your code in the Cloud in the shortest amount of time. It is based on real world examples and covers all of the languages available to you in OpenShift.', 3.99, 1);
insert into book (id, created, title, description, price, image_id) values (2, '2014-03-01', 'Log everything!','Big commercial websites breathe data: they create a lot of it very fast, but also need the feedback based on the very same data to become better and better. In this book, we present our journey towards a very generic solution to gather and utilize all data produced by our web application and associated systems, be it technical or business data. We show what technologies we have evaluated and which tools emerged in the end as the (currently) best solution for us. By showing you our ideas, our process, the drawbacks and the solutions, we provide a guide towards building your own data infrastructure. Further, we explore the possibilities to access and harness the data using the map/reduce approach in order to prepare you for the most challenging part of it all: gaining relevant knowledge you did not had before.', 3.99, 2);
insert into book (id, created, title, description, price, image_id) values (3, '2012-12-01', 'Presenting for Geeks', 'A presentation is not about the content or about you - it''s about your audience. Your job as a presenter is to take your audience to a place where they know about your topic, understand it and act on it. This book will show you how to achieve this.', 3.99, 3);
insert into book (id, created, title, description, price, image_id) values (4, '2013-03-01', 'Clojure Made Simple - Introduction to Clojure','Learning a new language and its associated paradigm and tools is challenging, especially with our hectic schedules. With "Clojure Made Simple" there is enough guidance to get you started with Clojure, a modern lisp for the Java Virtual Machine. Learning Clojure is a great way to start thinking in a functional way, something that is invaluable as our underlying hardware changes to be more scalable through the number of processor cores rather than individual speed. By introducing you to the important parts of the Clojure language in a practical way, this book gives you a jump start in learning Clojure and gets you productive quickly. It will help you understand the value of discovering Clojure in more depth.', 3.99, 4);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

delete from book;
delete from s3image;
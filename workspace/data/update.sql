alter table groupmember drop column permissions;
alter table groupmember add column permissions int default null;



alter table sport add column details2 Text;
update sport set details2 = details;
alter table sport drop column details;
alter table sport CHANGE details2 details Text;

<!-- use the following to extend the length of sport details -->
alter table sport CHANGE details details Text;


insert into organisation (id, enabled, userName, ownerid) values (0, true, "first", 5);
select * from organisation;

insert into grouping (id, userName, ownerOrganisationId) values (0, "firstGroup", 0);
select * from grouping;

insert into groupMember (id, enabled, groupId, permissions, userID) values (0, true, 0, 2, 5);
select * from groupMember;

insert into groupMember (id, enabled, groupId, userID, permissions) values (2, true, 12, 13,2);
select * from groupMember;





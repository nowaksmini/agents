CREATE LOGIN minipw WITH PASSWORD = 'minipw';

GO

CREATE USER smartroad FOR LOGIN minipw

GO

GRANT SELECT TO smartroad

GO

GRANT INSERT TO smartroada

GO

GRANT UPDATE TO smartroad

GO

GRANT DELETE TO smartroad

GO
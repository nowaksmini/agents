USE [SmartRoad]
GO

ALTER TABLE [dbo].[stations]  WITH CHECK ADD  CONSTRAINT [FK__stations__addres] FOREIGN KEY([address_id])
REFERENCES [dbo].[addresses] ([id])
GO

ALTER TABLE [dbo].[stations] CHECK CONSTRAINT [FK__stations__addres]
GO
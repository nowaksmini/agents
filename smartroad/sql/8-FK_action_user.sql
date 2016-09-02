USE [SmartRoad]
GO

ALTER TABLE [dbo].[actions]
  WITH CHECK ADD CONSTRAINT [FK__actions__users] FOREIGN KEY ([user_id])
REFERENCES [dbo].[users] ([id])
GO

ALTER TABLE [dbo].[actions]
  CHECK CONSTRAINT [FK__actions__users]
GO
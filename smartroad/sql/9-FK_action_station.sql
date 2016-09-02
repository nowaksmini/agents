USE [SmartRoad]
GO

ALTER TABLE [dbo].[actions]
  WITH CHECK ADD CONSTRAINT [FK__actions__stations] FOREIGN KEY ([station_id])
REFERENCES [dbo].[stations] ([id])
GO

ALTER TABLE [dbo].[actions]
  CHECK CONSTRAINT [FK__actions__stations]
GO
USE [SmartRoad]
GO

ALTER TABLE [dbo].[coupons]
  WITH CHECK ADD CONSTRAINT [FK__coupons__actions] FOREIGN KEY ([action_id])
REFERENCES [dbo].[actions] ([id])
GO

ALTER TABLE [dbo].[coupons]
  CHECK CONSTRAINT [FK__coupons__actions]
GO
USE [SmartRoad]
GO

/****** Object:  Index [NonClusteredIndex-token]    Script Date: 2016-09-02 03:57:46 ******/
CREATE NONCLUSTERED INDEX [NonClusteredIndex-token] ON [dbo].[users]
(
	[token] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
USE SmartRoad
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [stations] (
  [id]          [int] IDENTITY (1, 1) NOT NULL,
  [token]       [varchar](100)        NOT NULL UNIQUE,
  [name]        [nvarchar](100)       NOT NULL,
  [full_name]   [nvarchar](100)       NULL,
  [logo]        [nvarchar](200)       NULL,
  [email]       [varchar](100)        NULL,
  [phone]       [varchar](100)        NULL,
  [longitude]   [FLOAT]               NOT NULL,
  [latitude]    [FLOAT]               NOT NULL,
  [address_id]  [int]                 NULL,
  [create_date] [datetime]            NULL,
  [modify_date] [datetime]            NULL,
  CONSTRAINT [PK_stations] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 80)
    ON [PRIMARY]
) ON [PRIMARY]

GO


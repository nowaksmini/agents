USE SmartRoad
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [addresses] (
  [id]          [int] IDENTITY (1, 1) NOT NULL,
  [country]     [nvarchar](100)       NULL,
  [distinct]    [nvarchar](100)       NULL,
  [city]        [nvarchar](100)       NULL,
  [street]      [varchar](100)        NULL,
  [number]      [varchar](100)        NULL,
  [extraNumber] [varchar](100)        NULL,
  [postalCode]  [varchar](100)        NULL,
  [create_date] [datetime]            NULL,
  [modify_date] [datetime]            NULL,
  CONSTRAINT [PK_addresses] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 80)
    ON [PRIMARY]
) ON [PRIMARY]

GO

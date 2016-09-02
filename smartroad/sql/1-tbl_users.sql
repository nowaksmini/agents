USE SmartRoad
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [users] (
  [id]          [int] IDENTITY (1, 1) NOT NULL,
  [first_name]  [varchar](100)        NULL,
  [last_name]   [varchar](100)        NULL,
  [email]       [varchar](100)        NOT NULL UNIQUE,
  [create_date] [datetime]            NULL,
  [modify_date] [datetime]            NULL,
  [token]       [varchar](100)        NOT NULL UNIQUE,
  [password]    [varchar](100)        NOT NULL,
  CONSTRAINT [PK_users] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 80)
    ON [PRIMARY]
) ON [PRIMARY]

GO


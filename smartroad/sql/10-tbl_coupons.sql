USE SmartRoad
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [coupons] (
  [id]            [int] IDENTITY (1, 1) NOT NULL,
  [token]         [varchar](100)        NOT NULL UNIQUE,
  [value]         [bit]                 NULL,
  [discount_code] [varchar](100)        NOT NULL,
  [action_id]     [int]                 NOT NULL,
  [time_of_use]   [datetime]            NULL,
  [valid_from]    [datetime]            NOT NULL,
  [valid_to]      [datetime]            NOT NULL,
  [create_date]   [datetime]            NULL,
  [modify_date]   [datetime]            NULL,
  CONSTRAINT [PK_coupons] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 80)
    ON [PRIMARY]
) ON [PRIMARY]

GO


USE SmartRoad
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [actions] (
  [id]           [int] IDENTITY (1, 1) NOT NULL,
  [token]        [varchar](100)        NOT NULL UNIQUE,
  [action_type]  [varchar](100)        NOT NULL,
  [value]        [bit]                 NULL,
  [sent_confirm] [bit]                 NULL,
  [station_id]   [int]                 NOT NULL,
  [user_id]      [int]                 NOT NULL,
  [date_from]    [datetime]            NOT NULL,
  [date_to]      [datetime]            NOT NULL,
  [create_date]  [datetime]            NULL,
  [modify_date]  [datetime]            NULL,
  CONSTRAINT [PK_actions] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 80)
    ON [PRIMARY]
) ON [PRIMARY]

GO


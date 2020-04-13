SELECT
  c.id,
  c.code,
  c.name,
  c.legal_address,
  c.manager_id,
  m.name AS manager_name,
  m.patronymic AS manager_patronymic,
  m.surname AS manager_surname,
  m.phone_number AS manager_phone_number,
  m.deputy_id AS deputy_manager_id,
  m.deputy_name AS deputy_manager_name,
  m.deputy_patronymic AS deputy_manager_patronymic,
  m.deputy_surname AS deputy_manager_surname,
  m.deputy_phone_number AS deputy_manager_phone_number
FROM
  vct_client c
    LEFT JOIN (
    SELECT
      mm.id,
      mm.name,
      mm.patronymic,
      mm.surname,
      mm.phone_number,
      mm.deputy_id,
      dm.name AS deputy_name,
      dm.patronymic AS deputy_patronymic,
      dm.surname AS deputy_surname,
      dm.phone_number AS deputy_phone_number
    FROM
      vct_manager mm
        LEFT JOIN (
        SELECT
          id,
          deputy_id,
          name,
          patronymic,
          surname,
          phone_number,
          removal_date
        FROM
          vct_manager
        WHERE
          removal_date IS NULL
        ORDER BY
          id
      ) dm
      ON dm.ID = mm.deputy_id
    WHERE
      mm.removal_date IS NULL
    ORDER BY
      mm.id
  ) m
  ON m.ID = c.manager_id
WHERE
  c.removal_date IS NULL
ORDER BY
  c.id

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
  mm.id = :id
  AND mm.removal_date IS NULL

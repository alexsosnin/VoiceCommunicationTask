SELECT
  c.id,
  c.code,
  c.name,
  c.legal_address,
  c.manager_id
FROM
  vct_client c
WHERE
  c.removal_date IS NULL
  AND c.manager_id = :manager_id
ORDER BY
  c.id

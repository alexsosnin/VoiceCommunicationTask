UPDATE vct_client
SET
  manager_id = :new_manager_id
WHERE
  removal_date IS NULL
  AND manager_id = :manager_id

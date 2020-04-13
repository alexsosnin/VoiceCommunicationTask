UPDATE vct_client
SET
  name          = :name,
  legal_address = :legal_address,
  manager_id    = :manager_id
WHERE id = :id

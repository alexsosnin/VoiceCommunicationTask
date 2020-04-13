UPDATE vct_manager
SET
  removal_date = :removal_date
WHERE id = :id
  AND removal_date IS NULL

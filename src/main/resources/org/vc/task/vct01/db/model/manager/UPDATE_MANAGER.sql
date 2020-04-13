UPDATE vct_manager
SET
  name         = :name,
  patronymic   = :patronymic,
  surname      = :surname,
  phone_number = :phone_number,
  deputy_id    = :deputy_id
WHERE id = :id

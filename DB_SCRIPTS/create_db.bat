SET Path=%Path%;C:\Program Files\PostgreSQL\11\bin

dropdb --host="localhost" --port="5432" --username="postgres" "vctask"

createdb --host="localhost" --port="5432" --username="postgres" "vctask"
	


SELECT routine_schema,
       routine_name
FROM   information_schema.routines
WHERE  routine_type = 'PROCEDURE'
  AND  routine_schema NOT IN ('pg_catalog', 'information_schema')
ORDER BY routine_schema, routine_name;
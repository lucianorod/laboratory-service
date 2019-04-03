# laboratory-service

API responsável pela manutenção de laboratórios e exames

Frameworks/libs utilizadas:

- Spring boot
- Spring data
- Hibernate
- Swagger
- Lombok
- ModelMapper
- Fixture factory

# Definições de projeto

- Tipos de exames

1) ANÁLISE CLINICA
2) IMAGEM

- Os tipos de exames foram criados como String e estão salvos no banco de dados, visando uma maior flexibilidade.
Para adicionar um novo tipo de exame, bastaria adicionar diretamente no banco de dados (má prática) ou através 
de um CRUD específico pra essa tarefa.<br>
Isso permite que sejam adicionados novos tipos de exames de acordo com a demanda e sem a necessidade
de um deploy para cada novo tipo, situação que aconteceria caso os tipos fossem mapeados como um ENUM.

- Não é permitido cadastrar um novo exame com o mesmo nome

- Os endpoints de operação em lote foram mapeados para rotas diferentes das inserções únicas.<br>
Essa decisão foi tomada visando uma melhor organização da API, onde operações únicas sempre terão um
único retorno e operações em lote sempre terão uma lista como retorno.

- A entidade que representa a chave composta do relacionamento de laboratórios com exames,
foi mapeada com um ID específico para diminuir a complexidade, caso surjam novas atributos
para essa entidade.

- Um exame não pode estar associado mais de uma vez a um mesmo laboratório.

# CRUD: Laboratório

- Criação de um novo laboratório:

```
curl -X POST \
  http://localhost:8080/laboratory-service/laboratories/ \
  -H 'Content-Type: application/json' \
  -d '{
	"name": "Lavosier",
	"address": {
		"street": "Avenida Jabaquara",
		"neighborhood": "Mirandópolis",
		"streetNumber": "459",
		"postalCode": "04140-000"
	}
}'
```

- Obter um laboratório existente:
```
curl -X GET \
  http://localhost:8080/laboratory-service/laboratories/1
```

- Atualização de um laboratório existente:
```
curl -X PUT \
  http://localhost:8080/laboratory-service/laboratories/1 \
  -H 'Content-Type: application/json' \
  -d '{
	"name": "Lavosier",
	"address": {
		"street": "Avenida Jabaquara",
		"neighborhood": "Mirandópolis",
		"streetNumber": "567",
		"complement": "Sala 201",
		"postalCode": "04140-000"
	}
}'
```
- Remover um laboratório existante:
```
curl -X DELETE \
  http://localhost:8080/laboratory-service/laboratories/1 
```

# CRUD: Exame

- Criação de um novo exame:
```
curl -X POST \
  http://localhost:8080/laboratory-service/exams \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "HEMOGRAMA",
    "examType": "ANALISE CLINICA"
}'
```

- Obter um exame existente:

```
curl -X GET \
  http://localhost:8080/laboratory-service/exams/1
```
- Atualização de um exame existente:
```
curl -X PUT \
  http://localhost:8080/laboratory-service/exams/1 \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "HEMOGRAMA COMPLETO",
    "examType": "ANALISE CLINICA"
}'
```

- Remover um exame existente:
```
curl -X DELETE \
  http://localhost:8080/laboratory-service/exams/1
```

# Operações de associação

- Associar um exame a um laboratório:

```
curl -X POST \
  http://localhost:8080/laboratory-service/laboratories/1/exams/1
```

- Desassociar um exame de um laboratório:

```
curl -X DELETE \
  http://localhost:8080/laboratory-service/laboratories/1/exams/1
```

# Operações em lote: Laboratório

- Criação em lote de laboratórios:

```
curl -X POST \
  http://localhost:8080/laboratory-service/laboratories/bulk \
  -H 'Content-Type: application/json' \
  -d '[{
	"name": "Lavosier",
	"address": {
		"street": "Avenida Jabaquara",
		"neighborhood": "Mirandópolis",
		"streetNumber": "459",
		"postalCode": "04140-000"
	}
},
{
	"name": "Delboni",
	"address": {
		"street": "Avenida Jabaquara",
		"neighborhood": "Mirandópolis",
		"streetNumber": "550",
		"postalCode": "04140-000"
	}
}]'
```

- Atualização em lote de laboratórios:
```
curl -X PUT \
  http://localhost:8080/laboratory-service/laboratories/bulk \
  -H 'Content-Type: application/json' \
  -d '[
    {
        "id": 8,
        "name": "Delboni",
        "address": {
            "id": 11,
            "street": "Avenida Jabaquara",
            "neighborhood": "Mirandópolis",
            "streetNumber": "550",
            "complement": "Sala 3",
            "postalCode": "04140-000"
        }
    },
    {
        "id": 9,
        "name": "Lavosier",
        "address": {
            "id": 12,
            "street": "Avenida Jabaquara",
            "neighborhood": "Mirandópolis",
            "streetNumber": "459",
            "complement": "Sala 20",
            "postalCode": "04140-000"
        }
    }
]'
```

- Remoção em lote de laboratórios:

```
curl -X DELETE \
  'http://localhost:8080/laboratory-service/laboratories/bulk?ids=8,11' \
```

## Operações em lote: Exames

- Criação em lote de exames:

```
curl -X POST \
  http://localhost:8080/laboratory-service/exams/bulk \
  -H 'Content-Type: application/json' \
  -d '[{
    "name": "HEMOGRAMA",
    "examType": "ANALISE CLINICA"
},
{
    "name": "COLESTEROL",
    "examType": "ANALISE CLINICA"
}]'
```

- Atualização em lote de exames:

```
curl -X PUT \
  http://localhost:8080/laboratory-service/exams/bulk \
  -H 'Content-Type: application/json' \
  -d '[
    {
        "id": 18,
        "name": "COLESTEROL",
        "examType": "ANALISE CLINICA"
    },
    {
        "id": 19,
        "name": "HEMOGRAMA",
        "examType": "ANALISE CLINICA"
    }
]'
```

- Remoção em lote de laboratórios:

```
curl -X DELETE \
  'http://localhost:8080/laboratory-service/exams/bulk?ids=1,2' \
```

# Melhorias

1) Definir um identificador forte para os laboratórios, pois não existe restrição para que sejam criados vários 
laboratórios com os mesmos dados
2) Validação de CEP
3) Teste para cenários de exceção
4) Uma remoção de associação deve ser tratada como física ou lógica?

# Deploy

```
./gradlew clean build
docker build -t laboratory .
docker-compose up
```
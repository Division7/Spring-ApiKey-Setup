package edu.ucsb.cs156.autograderproxy

import org.springframework.data.repository.CrudRepository

interface ApiKeyRepository : CrudRepository<StoredApiKey, String> {
}
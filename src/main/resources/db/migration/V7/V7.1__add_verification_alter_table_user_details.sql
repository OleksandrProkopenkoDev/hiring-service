drop table if exists verifications;

CREATE TABLE verifications
(
    id                        bigserial PRIMARY KEY,
    user_keycloak_id          UUID         NOT NULL UNIQUE,
    verification_state        VARCHAR(255) NOT NULL,
    tax_identification_number VARCHAR(10)  NOT NULL,
    document_images_id        VARCHAR(255) NOT NULL,
    rejection_reason          VARCHAR(255),
    is_deleted                BOOLEAN DEFAULT FALSE
);

-- Remove the verification_state column (make sure you have handled any existing data appropriately)
ALTER TABLE user_details
    -- Drop the foreign key constraint
    DROP CONSTRAINT IF EXISTS user_details_user_id_fkey,

    -- Drop the column associated with the foreign key constraint
    DROP COLUMN IF EXISTS user_id,

    ADD COLUMN user_keycloak_id UUID NOT NULL UNIQUE,

    DROP COLUMN IF EXISTS document_images_url,

    DROP COLUMN IF EXISTS verification_state,

    DROP COLUMN IF EXISTS father_name,

    ADD COLUMN middle_name      VARCHAR(255)
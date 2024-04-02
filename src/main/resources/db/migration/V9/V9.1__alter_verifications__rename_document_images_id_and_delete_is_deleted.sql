ALTER TABLE verifications

    DROP COLUMN IF EXISTS is_deleted;

ALTER TABLE verifications

    RENAME COLUMN document_images_id TO verification_images_id

-- Roles

INSERT INTO role (id, role, description, created_at, updated_at)
VALUES (1, 'ADMIN', 'Application-level admin with full privileges', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO role (id, role, description, created_at, updated_at)
VALUES (2, 'TEAM_ADMIN', 'Team-level admin who can manage team and members', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO role (id, role, description, created_at, updated_at)
VALUES (3, 'BOARD_ADMIN', 'Board-level admin with lane and label control', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO role (id, role, description, created_at, updated_at)
VALUES (4, 'BOARD_MEMBER', 'Board member who can manage tickets', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO role (id, role, description, created_at, updated_at)
VALUES (5, 'VIEWER', 'Read-only access to boards and tickets', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO role (id, role, description, created_at, updated_at)
VALUES (6, 'USER', 'Default role assigned when a user account is created', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

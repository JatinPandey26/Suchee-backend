--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

-- Started on 2025-06-09 01:26:11

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 25630)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS public;


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 25645)
-- Name: attachment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.attachment (
    created_at timestamp(6) without time zone,
    created_by bigint,
    file_size bigint,
    id bigint NOT NULL,
    last_user_id bigint,
    updated_at timestamp(6) without time zone,
    att_upld_status character varying(255),
    file_type character varying(255),
    name character varying(255),
    path character varying(255),
    pre_upload_url character varying(255),
    preview_url character varying(255),
    storage_provider character varying(255),
    url character varying(255),
    uploaded_by bytea,
    CONSTRAINT attachment_att_upld_status_check CHECK (((att_upld_status)::text = ANY ((ARRAY['PENDING'::character varying, 'FAILED'::character varying, 'COMPLETED'::character varying])::text[]))),
    CONSTRAINT attachment_file_type_check CHECK (((file_type)::text = ANY ((ARRAY['IMAGE'::character varying, 'VIDEO'::character varying, 'AUDIO'::character varying, 'DOCUMENT'::character varying, 'SPREADSHEET'::character varying, 'PRESENTATION'::character varying, 'ARCHIVE'::character varying, 'CODE'::character varying, 'OTHER'::character varying])::text[]))),
    CONSTRAINT attachment_storage_provider_check CHECK (((storage_provider)::text = ANY ((ARRAY['AWS'::character varying, 'CLOUDINARY'::character varying])::text[])))
);


ALTER TABLE public.attachment OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 25640)
-- Name: attachment_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.attachment_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.attachment_seq OWNER TO postgres;




--
-- TOC entry 222 (class 1259 OID 25655)
-- Name: member; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.member (
    created_at timestamp(6) without time zone,
    created_by bigint,
    id bigint NOT NULL,
    last_user_id bigint,
    team_id bigint,
    updated_at timestamp(6) without time zone,
    user_id bigint,
    notification_preference character varying(255),
    status character varying(255),
    CONSTRAINT member_notification_preference_check CHECK (((notification_preference)::text = ANY ((ARRAY['ALL'::character varying, 'MENTIONS'::character varying, 'NONE'::character varying])::text[]))),
    CONSTRAINT member_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INVITED'::character varying, 'REMOVED'::character varying, 'LEFT'::character varying])::text[])))
);


ALTER TABLE public.member OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 25669)
-- Name: member_invitation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.member_invitation (
    created_at timestamp(6) without time zone,
    created_by bigint,
    id bigint NOT NULL,
    last_user_id bigint,
    role bigint,
    updated_at timestamp(6) without time zone,
    email character varying(255),
    status character varying(255),
    team bytea,
    CONSTRAINT member_invitation_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'SENT'::character varying, 'ACCEPTED'::character varying, 'DECLINED'::character varying, 'EXPIRED'::character varying])::text[])))
);


ALTER TABLE public.member_invitation OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 25641)
-- Name: member_invitation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.member_invitation_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.member_invitation_seq OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 25642)
-- Name: member_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.member_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.member_seq OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 25664)
-- Name: member_x_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.member_x_role (
    member_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.member_x_role OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 25680)
-- Name: notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notification (
    retry_count integer NOT NULL,
    created_at timestamp(6) without time zone,
    created_by bigint,
    id bigint NOT NULL,
    last_retried_at timestamp(6) without time zone,
    last_user_id bigint,
    updated_at timestamp(6) without time zone,
    failure_reason character varying(255),
    recipients character varying(255),
    reference_id character varying(255),
    status character varying(255),
    subject character varying(255),
    template character varying(255),
    type character varying(255) NOT NULL,
    message oid,
    CONSTRAINT notification_status_check CHECK (((status)::text = ANY ((ARRAY['SENT'::character varying, 'FAILED'::character varying, 'PENDING'::character varying])::text[]))),
    CONSTRAINT notification_type_check CHECK (((type)::text = ANY ((ARRAY['EMAIL'::character varying, 'SMS'::character varying, 'PUSH'::character varying])::text[])))
);


ALTER TABLE public.notification OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 25679)
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.notification ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 227 (class 1259 OID 25689)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    created_at timestamp(6) without time zone,
    created_by bigint,
    id bigint NOT NULL,
    last_user_id bigint,
    updated_at timestamp(6) without time zone,
    description character varying(255),
    role character varying(255) NOT NULL,
    CONSTRAINT role_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'TEAM_ADMIN'::character varying, 'TEAM_MEMBER'::character varying, 'BOARD_ADMIN'::character varying, 'BOARD_MEMBER'::character varying, 'VIEWER'::character varying, 'USER'::character varying])::text[])))
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 25697)
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    created_at timestamp(6) without time zone,
    created_by bigint,
    id bigint NOT NULL,
    last_user_id bigint,
    updated_at timestamp(6) without time zone,
    name character varying(60) NOT NULL,
    avatar character varying(600),
    description character varying(255)
);


ALTER TABLE public.team OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 25643)
-- Name: team_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.team_seq OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 25709)
-- Name: user_account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_account (
    dob date NOT NULL,
    created_at timestamp(6) without time zone,
    created_by bigint,
    id bigint NOT NULL,
    last_user_id bigint,
    updated_at timestamp(6) without time zone,
    avatar character varying(600),
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255),
    password character varying(255) NOT NULL
);


ALTER TABLE public.user_account OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 25644)
-- Name: user_account_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_account_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_account_seq OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 25706)
-- Name: user_account_x_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_account_x_role (
    role_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.user_account_x_role OWNER TO postgres;

--
-- TOC entry 4743 (class 2606 OID 25654)
-- Name: attachment attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (id);

-- TOC entry 4749 (class 2606 OID 25676)
-- Name: member_invitation member_invitation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_invitation
    ADD CONSTRAINT member_invitation_pkey PRIMARY KEY (id);


--
-- TOC entry 4751 (class 2606 OID 25678)
-- Name: member_invitation member_invitation_role_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_invitation
    ADD CONSTRAINT member_invitation_role_key UNIQUE (role);


--
-- TOC entry 4745 (class 2606 OID 25663)
-- Name: member member_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT member_pkey PRIMARY KEY (id);


--
-- TOC entry 4747 (class 2606 OID 25668)
-- Name: member_x_role member_x_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_x_role
    ADD CONSTRAINT member_x_role_pkey PRIMARY KEY (member_id, role_id);


--
-- TOC entry 4753 (class 2606 OID 25688)
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);


--
-- TOC entry 4755 (class 2606 OID 25696)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 4757 (class 2606 OID 25705)
-- Name: team team_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_name_key UNIQUE (name);


--
-- TOC entry 4759 (class 2606 OID 25703)
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- TOC entry 4761 (class 2606 OID 25717)
-- Name: user_account user_account_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT user_account_email_key UNIQUE (email);


--
-- TOC entry 4763 (class 2606 OID 25715)
-- Name: user_account user_account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- TOC entry 4766 (class 2606 OID 25743)
-- Name: member fk1ikd302qh85062tlmwibvoo20; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT fk1ikd302qh85062tlmwibvoo20 FOREIGN KEY (user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4779 (class 2606 OID 25793)
-- Name: team fk1ol72ca2ekkem2pbqdcspueli; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT fk1ol72ca2ekkem2pbqdcspueli FOREIGN KEY (created_by) REFERENCES public.user_account(id);


--
-- TOC entry 4777 (class 2606 OID 25788)
-- Name: role fk2b5qhj4r559n8kiifhedyvwyk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT fk2b5qhj4r559n8kiifhedyvwyk FOREIGN KEY (last_user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4767 (class 2606 OID 25733)
-- Name: member fk441en2jn8x36o34af8lcklr09; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT fk441en2jn8x36o34af8lcklr09 FOREIGN KEY (last_user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4781 (class 2606 OID 25803)
-- Name: user_account_x_role fk49ubruldbjpdqjwxjy9jmqxco; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account_x_role
    ADD CONSTRAINT fk49ubruldbjpdqjwxjy9jmqxco FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 4772 (class 2606 OID 25768)
-- Name: member_invitation fk50uuyqt5lj6k66w7nnjxc4hqk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_invitation
    ADD CONSTRAINT fk50uuyqt5lj6k66w7nnjxc4hqk FOREIGN KEY (role) REFERENCES public.role(id);


--
-- TOC entry 4775 (class 2606 OID 25778)
-- Name: notification fk5x80bhiarxo0x2pu20umftjfh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fk5x80bhiarxo0x2pu20umftjfh FOREIGN KEY (last_user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4770 (class 2606 OID 25753)
-- Name: member_x_role fk7728esk7xfmucad99n6g3wb75; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_x_role
    ADD CONSTRAINT fk7728esk7xfmucad99n6g3wb75 FOREIGN KEY (member_id) REFERENCES public.member(id);


--
-- TOC entry 4768 (class 2606 OID 25738)
-- Name: member fkcjte2jn9pvo9ud2hyfgwcja0k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT fkcjte2jn9pvo9ud2hyfgwcja0k FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- TOC entry 4764 (class 2606 OID 25723)
-- Name: attachment fkd2tboxoaieagsft6aagd9gv8o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fkd2tboxoaieagsft6aagd9gv8o FOREIGN KEY (last_user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4765 (class 2606 OID 25718)
-- Name: attachment fkgcw81kawnsp9r4200te4dwg8a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fkgcw81kawnsp9r4200te4dwg8a FOREIGN KEY (created_by) REFERENCES public.user_account(id);


--
-- TOC entry 4782 (class 2606 OID 25808)
-- Name: user_account_x_role fkh8wn4lvjqtae31vgm28923q0b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account_x_role
    ADD CONSTRAINT fkh8wn4lvjqtae31vgm28923q0b FOREIGN KEY (user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4778 (class 2606 OID 25783)
-- Name: role fkhj9waq1s399s2k3q5n4s5tu1n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT fkhj9waq1s399s2k3q5n4s5tu1n FOREIGN KEY (created_by) REFERENCES public.user_account(id);


--
-- TOC entry 4773 (class 2606 OID 25758)
-- Name: member_invitation fkhk9fe31xrryf0vrva688qth9p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_invitation
    ADD CONSTRAINT fkhk9fe31xrryf0vrva688qth9p FOREIGN KEY (created_by) REFERENCES public.user_account(id);


--
-- TOC entry 4774 (class 2606 OID 25763)
-- Name: member_invitation fkiqescwsigyn7h8yrllwgjkoyc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_invitation
    ADD CONSTRAINT fkiqescwsigyn7h8yrllwgjkoyc FOREIGN KEY (last_user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4780 (class 2606 OID 25798)
-- Name: team fkjiudphdv7ihe4kwd2c7735pxn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT fkjiudphdv7ihe4kwd2c7735pxn FOREIGN KEY (last_user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4769 (class 2606 OID 25728)
-- Name: member fkkyf6v8k00cgxa0f414todqo1w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT fkkyf6v8k00cgxa0f414todqo1w FOREIGN KEY (created_by) REFERENCES public.user_account(id);


--
-- TOC entry 4783 (class 2606 OID 25813)
-- Name: user_account fkomgecnywqcj7beux1y9joy6ne; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT fkomgecnywqcj7beux1y9joy6ne FOREIGN KEY (created_by) REFERENCES public.user_account(id);


--
-- TOC entry 4771 (class 2606 OID 25748)
-- Name: member_x_role fkpa7c5yxfuaba55krr7wwqwrfp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member_x_role
    ADD CONSTRAINT fkpa7c5yxfuaba55krr7wwqwrfp FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 4784 (class 2606 OID 25818)
-- Name: user_account fkrr9v5o3wx5ko18qm0oc4b2jhv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT fkrr9v5o3wx5ko18qm0oc4b2jhv FOREIGN KEY (last_user_id) REFERENCES public.user_account(id);


--
-- TOC entry 4776 (class 2606 OID 25773)
-- Name: notification fktgubjbfeqk655hv4g78jqfagi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fktgubjbfeqk655hv4g78jqfagi FOREIGN KEY (created_by) REFERENCES public.user_account(id);


-- Completed on 2025-06-09 01:26:11

--
-- PostgreSQL database dump complete
--


alter table numero add versao int;

commit work;

update numero set versao = 0;
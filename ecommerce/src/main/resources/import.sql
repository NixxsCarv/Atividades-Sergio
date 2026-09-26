insert into categoria (nome, descricao) values ('Papelaria', 'Material Escolar e de Escritório');
insert into categoria (nome, descricao) values ('Informática', 'Equipamentos e Acessórios de Informática');
insert into categoria (nome, descricao) values ('Vestuário', 'Roupas e Acessórios em Geral');
insert into categoria (nome, descricao) values ('Cozinha', 'Utensílios e Eletrodomésticos');
insert into categoria (nome, descricao) values ('Decoração', 'Itens para Decoração de Ambientes');

insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Kit de Canetas Coloridas', 'Conjunto com 12 canetas hidrográficas', 24.90, 40, 1);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Monitor LG UltraWide', 'Monitor 29 polegadas Full HD', 1899.00, 10, 2);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Camiseta Algodão', 'Camiseta básica 100% algodão', 49.90, 60, 3);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Liquidificador Philips', 'Liquidificador 5 velocidades 1000W', 219.90, 18, 4);
insert into produto (nome, descricao, preco, estoque, categoria_id) values ('Quadro Decorativo', 'Quadro decorativo para sala 60x40cm', 159.00, 12, 5);

insert into cliente (nome, email, telefone) values ('Larissa Andrade', 'larissa.andrade@email.com', '(18) 98822-2001');
insert into cliente (nome, email, telefone) values ('Rafael Tomazini', 'rafael.tomazini@email.com', '(18) 98822-2002');
insert into cliente (nome, email, telefone) values ('Camila Ferreira', 'camila.ferreira@email.com', '(18) 98822-2003');
insert into cliente (nome, email, telefone) values ('Thiago Bezerra', 'thiago.bezerra@email.com', '(18) 98822-2004');
insert into cliente (nome, email, telefone) values ('Juliana Martins', 'juliana.martins@email.com', '(18) 98822-2005');

insert into pedido (data, status, valor_total, cliente_id) values ('2026-05-04 10:00:00', 'FINALIZADO', 49.80, 1);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-05-07 15:20:00', 'FINALIZADO', 1899.00, 2);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-05-10 09:45:00', 'PAGO', 149.70, 3);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-05-13 17:10:00', 'PENDENTE', 439.80, 4);
insert into pedido (data, status, valor_total, cliente_id) values ('2026-05-16 12:30:00', 'CANCELADO', 159.00, 5);

insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (2, 24.90, 1, 1);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (1, 1899.00, 2, 2);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (3, 49.90, 3, 3);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (2, 219.90, 4, 4);
insert into item_pedido (quantidade, valor_unitario, pedido_id, produto_id) values (1, 159.00, 5, 5);

insert into pagamento (valor, data, status, tipo, pedido_id) values (49.80, '2026-05-04 10:05:00', 'APROVADO', 'PIX', 1);
insert into pagamento (valor, data, status, tipo, pedido_id) values (1899.00, '2026-05-07 15:25:00', 'APROVADO', 'CARTAO_CREDITO', 2);
insert into pagamento (valor, data, status, tipo, pedido_id) values (149.70, '2026-05-10 09:50:00', 'APROVADO', 'BOLETO', 3);
insert into pagamento (valor, data, status, tipo, pedido_id) values (439.80, '2026-05-13 17:15:00', 'PENDENTE', 'BOLETO', 4);
insert into pagamento (valor, data, status, tipo, pedido_id) values (159.00, '2026-05-16 12:35:00', 'RECUSADO', 'CARTAO_DEBITO', 5);
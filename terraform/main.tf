provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "backend_instance" {
  ami           = "ami-0866a3c8686eaeeba"  #ubuntu 24.04
  instance_type = "t3.medium"
  key_name      = "vockey"

  vpc_security_group_ids = [
    aws_security_group.backend-sg.id
  ]
  tags = {
    Name = "BACKEND-MACHINE"
  }
}

resource "aws_instance" "frontend_instance" {
  ami           = "ami-0866a3c8686eaeeba"  #ubuntu 24.04
  instance_type = "t3.medium"
  key_name      = "vockey"

  vpc_security_group_ids = [
    aws_security_group.frontend-sg.id
  ]

  tags = {
    Name = "FRONTEND-MACHINE"
  }
}

resource "aws_eip_association" "backend_eip_association" {
  instance_id = aws_instance.backend_instance.id
  public_ip   = local.backend_instance_ip
}

resource "aws_eip_association" "frontend_eip_association" {
  instance_id = aws_instance.frontend_instance.id
  public_ip   = local.frontend_instance_ip
}

locals {
  frontend_instance_ip = "52.20.47.142"
  backend_instance_ip  = "107.21.128.230"
#   rds_endpoint = aws_db_instance.postgres_instance2.endpoint
} 

resource "null_resource" "print_ip" {
  provisioner "local-exec" {
    command = "echo ${local.frontend_instance_ip} > ../frontend_public_ip.txt; echo ${local.backend_instance_ip} > ../backend_public_ip.txt;"
  }
  depends_on = [aws_instance.frontend_instance, aws_instance.backend_instance]
}

# resource "null_resource" "save_db_endpoint" {
#   provisioner "local-exec" {
#     command = "echo ${local.rds_endpoint} > ../rds_endpoint.txt"
#   }
#   depends_on = [aws_db_instance.postgres_instance2]
# }
resource "null_resource" "run_ansible_backend" {
  provisioner "local-exec" {
    command = "ANSIBLE_HOST_KEY_CHECKING=False ansible-playbook -i '${local.backend_instance_ip},' -u ubuntu --private-key ~/.ssh/labsuser.pem playbook-backend.yml"
  }
  depends_on = [aws_instance.backend_instance]
}

resource "null_resource" "run_ansible_frontend" {
  provisioner "local-exec" {
    command = "ANSIBLE_HOST_KEY_CHECKING=False ansible-playbook -i '${local.frontend_instance_ip},' -u ubuntu --private-key ~/.ssh/labsuser.pem playbook-frontend.yml"
  }
  depends_on = [aws_instance.frontend_instance]
}

resource "aws_security_group" "backend-sg" {
  name        = "backend-sg"

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  tags = {
    Name = "BackendSpringSG"
  }
}
resource "aws_security_group" "frontend-sg" {
  name        = "frontend-sg"

  ingress {
    from_port   = 4200
    to_port     = 4200
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  tags = {
    Name = "FrontendSpringSG"
  }
}

resource "aws_vpc_security_group_ingress_rule" "backend_allow_ssh_ipv4" {
  security_group_id = aws_security_group.backend-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 22
  to_port           = 22
  ip_protocol       = "tcp"
}

resource "aws_vpc_security_group_ingress_rule" "backend_allow_http_ipv4" {
  security_group_id = aws_security_group.backend-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 80
  to_port           = 80
  ip_protocol       = "tcp"
}

# resource "aws_vpc_security_group_egress_rule" "backend_allow_all_ipv4" {
#   security_group_id = aws_security_group.backend-sg.id
#   cidr_ipv4         = "0.0.0.0/0"
#   ip_protocol       = "-1"
# }

resource "aws_vpc_security_group_ingress_rule" "frontend_allow_ssh_ipv4" {
  security_group_id = aws_security_group.frontend-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 22
  to_port           = 22
  ip_protocol       = "tcp"
}

resource "aws_vpc_security_group_ingress_rule" "frontend_allow_http_ipv4" {
  security_group_id = aws_security_group.frontend-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 80
  to_port           = 80
  ip_protocol       = "tcp"
}

# resource "aws_vpc_security_group_egress_rule" "frontend_allow_all_ipv4" {
#   security_group_id = aws_security_group.frontend-sg.id
#   cidr_ipv4         = "0.0.0.0/0"
#   ip_protocol       = "-1"
# }

# resource "aws_security_group" "postgres_security_group2" {
#   name        = "postgres_security_group2"
#   description = "Security Group for PostgreSQL"

#   ingress {
#     from_port   = 5432
#     to_port     = 5432
#     protocol    = "tcp"
#     cidr_blocks = ["0.0.0.0/0"]
#   }

#   tags = {
#     Name = "postgres-db-sg_v2"
#   }
# }

# resource "aws_db_instance" "postgres_instance2" {
#   allocated_storage    = 20
#   engine               = "postgres"
#   instance_class       = "db.t3.micro"
#   identifier           = "postgrescloud2"
#   db_name              = "clouddb"
#   username             = "postgres"
#   password             = "postgres"
#   skip_final_snapshot  = true 
#   vpc_security_group_ids  = [aws_security_group.postgres_security_group2.id] 


#   # Ustawienia opcjonalne
#   storage_type          = "gp2" 
#   backup_retention_period = 7 
#   multi_az              = false
#   publicly_accessible   = true  

#   tags = {
#     Name = "postgres-db2"
#   }
# }

output "backend_instance_public_ip" {
  value = aws_instance.backend_instance.public_ip
}

output "frontend_instance_public_ip" {
  value = aws_instance.frontend_instance.public_ip
}

# output "rds_endpoint" {
#   value = aws_db_instance.postgres_instance2.endpoint
# }
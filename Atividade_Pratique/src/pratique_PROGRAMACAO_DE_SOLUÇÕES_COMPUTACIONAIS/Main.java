package pratique_PROGRAMACAO_DE_SOLUÇÕES_COMPUTACIONAIS;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Usuario> usuarios = new ArrayList<>();
    static List<Evento> eventos = new ArrayList<>();
    static final String ARQUIVO = "events.data";

    public static void main(String[] args) {
        carregarEventos();

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Cadastrar evento");
            System.out.println("3. Confirmar participação em evento");
            System.out.println("4. Cancelar participação");
            System.out.println("5. Consultar eventos futuros e ocorrendo agora");
            System.out.println("6. Histórico de eventos");
            System.out.println("7. Listar todos os usuários");  
            System.out.println("8. Sair");  
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: cadastrarUsuario(); break;
                case 2: cadastrarEvento(); break;
                case 3: confirmarParticipacao(); break;
                case 4: cancelarParticipacao(); break;
                case 5: consultarEventos(); break;
                case 6: historicoEventos(); break;
                case 7: listarUsuarios(); break; 
                case 8:  
                    salvarEventos();
                    rodando = false; 
                    System.out.println("Saindo..."); 
                    break;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    // ===================== Cadastro de usuário =====================
    public static void cadastrarUsuario() {
        System.out.println("\n=== Cadastro de Usuário ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario u = new Usuario(nome, email, telefone, endereco, login, senha);
        usuarios.add(u);
        System.out.println("Usuário cadastrado com sucesso!");
        System.out.println(u);
    }

    // ===================== Cadastro de evento =====================
    public static void cadastrarEvento() {
        System.out.println("\n=== Cadastro de Evento ===");

        // Categoria por número
        Categoria categoria = null;
        while (categoria == null) {
            System.out.println("Escolha a categoria do evento:");
            Categoria[] categorias = Categoria.values();
            for (int i = 0; i < categorias.length; i++)
                System.out.println((i + 1) + ". " + categorias[i]);
            System.out.print("Digite o número da categoria: ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= 1 && opcao <= categorias.length) categoria = categorias[opcao - 1];
                else System.out.println("Número inválido.");
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
            }
        }

        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço do evento: ");
        String endereco = scanner.nextLine();

        // Data
        LocalDate data = null;
        while (data == null) {
            System.out.print("Dia: "); int dia = Integer.parseInt(scanner.nextLine());
            System.out.print("Mês: "); int mes = Integer.parseInt(scanner.nextLine());
            System.out.print("Ano: "); int ano = Integer.parseInt(scanner.nextLine());
            data = LocalDate.of(ano, mes, dia);
            System.out.println("Data informada: " + data + ". Está correta? (y/n)");
            if (!scanner.nextLine().toLowerCase().equals("y")) data = null;
        }

        // Hora
        LocalTime hora = null;
        while (hora == null) {
            System.out.print("Hora (0-23): "); int h = Integer.parseInt(scanner.nextLine());
            System.out.print("Minuto (0-59): "); int m = Integer.parseInt(scanner.nextLine());
            hora = LocalTime.of(h, m);
            System.out.println("Hora informada: " + hora + ". Está correta? (y/n)");
            if (!scanner.nextLine().toLowerCase().equals("y")) hora = null;
        }

        LocalDateTime horario = LocalDateTime.of(data, hora);
        System.out.print("Descrição do evento: ");
        String descricao = scanner.nextLine();

        Evento e = new Evento(nome, endereco, categoria, horario, descricao);
        eventos.add(e);
        System.out.println("Evento cadastrado com sucesso!");
    }

    // ===================== Confirmar participação =====================
    public static void confirmarParticipacao() {
        Usuario usuario = autenticarUsuario();
        if (usuario == null) return;

        System.out.println("\n=== Lista de eventos ===");
        for (int i = 0; i < eventos.size(); i++)
            System.out.println((i+1) + ". " + eventos.get(i));

        System.out.print("Digite o número do evento para confirmar presença: ");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha >= 1 && escolha <= eventos.size()) {
            eventos.get(escolha-1).adicionarParticipante(usuario);
            System.out.println("Participação confirmada!");
        } else System.out.println("Opção inválida.");
    }

    // ===================== Cancelar participação =====================
    public static void cancelarParticipacao() {
        Usuario usuario = autenticarUsuario();
        if (usuario == null) return;

        List<Evento> meusEventos = new ArrayList<>();
        for (Evento e : eventos) if (e.estaParticipando(usuario)) meusEventos.add(e);

        if (meusEventos.isEmpty()) {
            System.out.println("Você não possui participação em eventos.");
            return;
        }

        System.out.println("\nEventos com participação confirmada:");
        for (int i = 0; i < meusEventos.size(); i++)
            System.out.println((i+1) + ". " + meusEventos.get(i));

        System.out.print("Digite o número do evento para cancelar presença: ");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha >= 1 && escolha <= meusEventos.size()) {
            meusEventos.get(escolha-1).removerParticipante(usuario);
            System.out.println("Participação cancelada!");
        } else System.out.println("Opção inválida.");
    }

    // ===================== Consultar eventos futuros e ocorrendo agora =====================
    public static void consultarEventos() {
        LocalDateTime agora = LocalDateTime.now();
        System.out.println("\n=== Eventos futuros ou ocorrendo agora ===");
        eventos.sort(Comparator.comparing(e -> e.horario));

        for (Evento e : eventos) {
            if (e.horario.isAfter(agora) || e.horario.isEqual(agora)) {
                String status = e.horario.isEqual(agora) ? "(Ocorre agora)" : "";
                System.out.println(e + " " + status);
            }
        }
    }

    // ===================== Histórico de eventos =====================
    public static void historicoEventos() {
        LocalDateTime agora = LocalDateTime.now();
        System.out.println("\n=== Eventos já ocorridos ===");
        for (Evento e : eventos) {
            if (e.horario.isBefore(agora)) System.out.println(e);
        }
    }

    // ===================== Autenticação =====================
    public static Usuario autenticarUsuario() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        for (Usuario u : usuarios) {
            if (u.login.equals(login) && u.senha.equals(senha)) return u;
        }
        System.out.println("Login ou senha incorretos!");
        return null;
    }

    // ===================== Salvar e carregar =====================
    public static void salvarEventos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            out.writeObject(eventos);
            out.writeObject(usuarios);
            System.out.println("Eventos e usuários salvos em " + ARQUIVO);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void carregarEventos() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            eventos = (List<Evento>) in.readObject();
            usuarios = (List<Usuario>) in.readObject();
            System.out.println("Eventos e usuários carregados de " + ARQUIVO);
        } catch (Exception e) {
            System.out.println("Nenhum arquivo encontrado. Iniciando sistema vazio.");
        }
    }
    
    // ===================== LISTAR USUÁRIOS =====================
    public static void listarUsuarios() {
        System.out.println("\n=== LISTA DE USUÁRIOS CADASTRADOS ===");
        
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado no sistema.");
            return;
        }
        
        System.out.println("Total de usuários: " + usuarios.size());
        System.out.println("----------------------------------------");
        
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            System.out.println((i + 1) + ". " + usuario.toString());
            
            // Mostra em quais eventos o usuário está participando
            List<Evento> eventosDoUsuario = new ArrayList<>();
            for (Evento evento : eventos) {
                if (evento.estaParticipando(usuario)) {
                    eventosDoUsuario.add(evento);
                }
            }
            
            if (!eventosDoUsuario.isEmpty()) {
                System.out.println("   Participa de " + eventosDoUsuario.size() + " evento(s):");
                for (Evento evento : eventosDoUsuario) {
                    System.out.println("   - " + evento.nome + " (" + evento.horario.toLocalDate() + ")");
                }
            } else {
                System.out.println("   Não participa de nenhum evento");
            }
            System.out.println("----------------------------------------");
        }
    }
}
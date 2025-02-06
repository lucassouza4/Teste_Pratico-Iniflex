package funcionarioTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import com.iniflex.testepratico.dao.FuncionarioDao;
import com.iniflex.testepratico.model.Funcionario;
import com.iniflex.testepratico.service.FuncionarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lucas
 */
public class FuncionarioServiceImplUnitTest {

    private FuncionarioDaoStub funcionarioDaoStub;
    private FuncionarioServiceImpl funcionarioService;
    private Funcionario funcionario1;
    private Funcionario funcionario2;

    static class FuncionarioDaoStub implements FuncionarioDao {

        private final List<Funcionario> funcionarios = new ArrayList<>();
        private List<Integer> mesesFiltro;

        @Override
        public void salvar(List<Funcionario> funcionarios) {
            this.funcionarios.addAll(funcionarios);
        }

        @Override
        public void salvar(Funcionario funcionario) {
            this.funcionarios.add(funcionario);
        }

        @Override
        public void atualizar(List<Funcionario> funcionarios) {
            this.funcionarios.clear();
            this.funcionarios.addAll(funcionarios);
        }

        @Override
        public void deletar(Long id) {
            funcionarios.removeIf(f -> f.getId().equals(id));
        }

        @Override
        public List<Funcionario> buscarTodos() {
            return new ArrayList<>(funcionarios);
        }

        @Override
        public List<Funcionario> buscarPorMeses(List<Integer> meses) {
            this.mesesFiltro = meses;
            return funcionarios.stream()
                    .filter(f -> meses.contains(f.getDataNascimento().getMonthValue()))
                    .toList();
        }

        @Override
        public Funcionario buscarFuncionarioMaisVelho() {
            return funcionarios.stream()
                    .min((f1, f2) -> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
                    .orElse(null);
        }

        @Override
        public void atualizar(Funcionario funcionario) {
            funcionarios.removeIf(f -> f.getId().equals(funcionario.getId()));
            funcionarios.add(funcionario);
        }
    }

    @BeforeEach
    public void setUp() {
        funcionarioDaoStub = new FuncionarioDaoStub();
        funcionarioService = FuncionarioServiceImpl.build(funcionarioDaoStub);

        funcionario1 = Funcionario.build(
                BigDecimal.valueOf(3000),
                "Desenvolvedor",
                "João Silva",
                LocalDate.of(1990, 5, 15)
        );
        funcionario1.setId(Long.valueOf("1"));

        funcionario2 = Funcionario.build(
                BigDecimal.valueOf(4000),
                "Gerente",
                "Maria Souza",
                LocalDate.of(1985, 10, 20)
        );
        funcionario1.setId(Long.valueOf("2"));
    }

    @Test
    void salvarFuncionarios_DeveSalvarLista() {
        List<Funcionario> funcionarios = List.of(funcionario1, funcionario2);

        funcionarioService.salvarFuncionarios(funcionarios);

        assertEquals(2, funcionarioDaoStub.buscarTodos().size());
        assertTrue(funcionarioDaoStub.buscarTodos().containsAll(funcionarios));
    }

    @Test
    void salvarFuncionario_DeveLerEntradasCorretamente() throws Exception {
        String input = "Carlos Oliveira\n15/05/1990\n5000\nAnalista\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.salvarFuncionario(reader);

        Funcionario salvo = funcionarioDaoStub.buscarTodos().get(0);
        assertEquals("Carlos Oliveira", salvo.getNome());
        assertEquals(LocalDate.of(1990, 5, 15), salvo.getDataNascimento());
        assertEquals(BigDecimal.valueOf(5000), salvo.getSalario());
    }

    @Test
    void salvarFuncionario_ComDataInvalida_DeveLidarComErro() throws Exception {
        String input = "Carlos Oliveira\n31/02/1990\n5000\nAnalista\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.salvarFuncionario(reader);

        assertTrue(funcionarioDaoStub.buscarTodos().isEmpty());
    }

    @Test
    void salvarFuncionario_ComSalarioInvalido_DeveLidarComErro() throws Exception {
        String input = "Carlos Oliveira\n15/05/1990\nabc\nAnalista\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.salvarFuncionario(reader);

        assertTrue(funcionarioDaoStub.buscarTodos().isEmpty());
    }

    @Test
    void atualizarTodosFuncionarios_ComAumento10Porcento() throws Exception {
        funcionarioDaoStub.salvar(List.of(funcionario1, funcionario2));
        String input = "1\n10\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.atualizarFuncionarios(reader);

        List<Funcionario> atualizados = funcionarioDaoStub.buscarTodos();
        assertEquals(BigDecimal.valueOf(3300).setScale(1), atualizados.get(0).getSalario());
        assertEquals(BigDecimal.valueOf(4400).setScale(1), atualizados.get(1).getSalario());
    }

    @Test
    void atualizarUmFuncionario_DeveAtualizarCorretamente() throws Exception {
        funcionarioDaoStub.salvar(funcionario1);
        String input = "2\n0\n1\nNovo Nome\n"; // opção 2(um funcionario), Índice 0, opção 1 (nome), novo nome
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.atualizarFuncionarios(reader);

        Funcionario atualizado = funcionarioDaoStub.buscarTodos().get(0);
        assertEquals("Novo Nome", atualizado.getNome());
    }

    @Test
    void atualizarFuncionario_ComSalarioNegativo_DeveLidarComErro() throws Exception {
        funcionarioDaoStub.salvar(funcionario1);
        String input = "2\n0\n4\n-1000\n"; // Salário negativo
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.atualizarFuncionarios(reader);

        assertEquals(BigDecimal.valueOf(3000), funcionarioDaoStub.buscarTodos().get(0).getSalario());
    }

    @Test
    void atualizarFuncionarios_ComPercentualInvalido_DeveLidarComErro() throws Exception {
        funcionarioDaoStub.salvar(List.of(funcionario1, funcionario2));
        String input = "1\nabc\n"; // Percentual inválido
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.atualizarFuncionarios(reader);

        List<Funcionario> funcionarios = funcionarioDaoStub.buscarTodos();
        assertEquals(BigDecimal.valueOf(3000), funcionarios.get(0).getSalario());
        assertEquals(BigDecimal.valueOf(4000), funcionarios.get(1).getSalario());
    }

    @Test
    void atualizarFuncionario_Inexistente_DeveLidarComErro() throws Exception {
        funcionarioDaoStub.salvar(funcionario1);
        String input = "2\n99\n1\nNovo Nome\n"; // Índice inválido
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.atualizarFuncionarios(reader);

        assertEquals("João Silva", funcionarioDaoStub.buscarTodos().get(0).getNome());
    }

    @Test
    void deletarFuncionario_DeveRemoverDoDao() throws Exception {
        funcionarioDaoStub.salvar(funcionario1);
        String input = "0\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.deletarFuncionario(reader);

        assertTrue(funcionarioDaoStub.buscarTodos().isEmpty());
    }

    @Test
    void deletarFuncionario_ComIndiceInvalido_DeveLidarComErro() throws Exception {
        funcionarioDaoStub.salvar(funcionario1);
        String input = "99\n"; // Índice inválido
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.deletarFuncionario(reader);

        assertEquals(1, funcionarioDaoStub.buscarTodos().size());
    }

    @Test
    void agruparFuncionarios_DeveAgruparPorFuncao() {
        funcionarioDaoStub.salvar(List.of(funcionario1, funcionario2));

        funcionarioService.agruparFuncionarios();

        List<Funcionario> funcionarios = funcionarioDaoStub.buscarTodos();
        assertEquals(2, funcionarios.size());
        assertTrue(funcionarios.stream().anyMatch(f -> f.getFuncao().equals("Desenvolvedor")));
        assertTrue(funcionarios.stream().anyMatch(f -> f.getFuncao().equals("Gerente")));
    }

    @Test
    void calcularTotalSalario_DeveSomarCorretamente() {
        funcionarioDaoStub.salvar(List.of(funcionario1, funcionario2));

        funcionarioService.calcularTotalSalario();

        BigDecimal total = funcionarioDaoStub.buscarTodos().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(7000), total);
    }

    @Test
    void calcularTotalSalario_ComListaVazia_DeveLidarComErro() {
        funcionarioService.calcularTotalSalario();

        BigDecimal total = funcionarioDaoStub.buscarTodos().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void buscarFuncionarioMaisVelho_DeveRetornarMaisAntigo() {
        Funcionario maisVelho = Funcionario.build(
                BigDecimal.valueOf(5000),
                "Diretor",
                "Antônio",
                LocalDate.of(1975, 1, 1)
        );
        funcionarioDaoStub.salvar(List.of(funcionario1, funcionario2, maisVelho));

        funcionarioService.buscarFuncionarioMaisVelho();

        Funcionario resultado = funcionarioDaoStub.buscarFuncionarioMaisVelho();
        assertEquals(maisVelho.getDataNascimento(), resultado.getDataNascimento());
    }

    @Test
    void buscarFuncionarioMaisVelho_ComListaVazia_DeveLidarComErro() {
        funcionarioService.buscarFuncionarioMaisVelho();

        assertNull(funcionarioDaoStub.buscarFuncionarioMaisVelho());
    }

    @Test
    void imprimirAniversariantes_DeveFiltrarMesesCorretos() throws Exception {
        funcionarioDaoStub.salvar(List.of(funcionario1, funcionario2));
        String input = "5,10\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.imprimirAniversariantes(reader);

        assertTrue(funcionarioDaoStub.mesesFiltro.containsAll(Arrays.asList(5, 10)));
        assertEquals(2, funcionarioDaoStub.buscarPorMeses(Arrays.asList(5, 10)).size());
    }

    @Test
    void imprimirAniversariantes_ComMesesInvalidos_DeveLidarComErro() throws Exception {
        funcionarioDaoStub.salvar(List.of(funcionario1, funcionario2));
        String input = "abc,xyz\n"; // Meses inválidos
        BufferedReader reader = new BufferedReader(new StringReader(input));

        funcionarioService.imprimirAniversariantes(reader);

        assertNull(funcionarioDaoStub.mesesFiltro);
    }
}

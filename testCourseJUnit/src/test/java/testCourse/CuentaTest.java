package testCourse;

import java.math.BigDecimal;
import java.util.Properties;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import exceptions.DineroInsuficienteException;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assumptions.*;

import testCourse.models.Banco;
import testCourse.models.Cuenta;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) //evita tener metodos static 
public class CuentaTest {
	
    Cuenta cuenta;

    private TestInfo testInfo;
    private TestReporter testReporter;
	
	/**
	 * 9. Ciclo de vida con anotaciones de Junit
	 */
	
	/*
	 * 9.1 @BeforeEach que se ejecuta en cada metodo test
	 */
    @BeforeEach
    void initMetodoTest(TestInfo testInfo, TestReporter testReporter) {
        this.cuenta = new Cuenta("Luis", new BigDecimal("1000.1234"));
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        System.out.println("iniciando el metodo.");
        testReporter.publishEntry(" ejecutando: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().orElse(null).getName()
                + " con las etiquetas " + testInfo.getTags());
    }
    
    /*
     * 9.2 @AfterEach la cual se ejecuta despues de cada test
     */
    @AfterEach
    void tearDown() {
        System.out.println("finalizando el metodo de prueba.");
    }
    
    /*
     * 9.3 @BeforeAll se ejecuta cuando inicializa todo el test 
     */
    @BeforeAll 
    static void beforeAll() {
        System.out.println("inicializando el test");
    }

    /*
     * 9.4 @AfterAll se ejecuta cuando termina todo el test
     */
    @AfterAll
    static void afterAll() {
        System.out.println("finalizando el test");
    }
    
    
	/**
	 * 1. Test que verifica si es un nombre valido
	 */
	@Test
	@DisplayName("testNombreConDisplayName") //se le asigna un nombre
	void testNombreCuenta() {
		//Cuenta cuenta = new Cuenta("Luis", new BigDecimal(1000.123));
		
		String esperado = "Luis";
		
		String real = cuenta.getPersona();
		assertNotNull(real, "la cunta no pueder ser nula"); //el segundo parametro es un mensaje
		assertEquals(esperado, real); //verifica si los dos valores son iguales
		assertTrue(real.equals("Luis"), () -> "nombre cuenta no es igual"); //por medio de una verificacion booleana se verifica si son igguales
	}
	
	/**
	 * 2. Test que verifica un slado valido
	 */
	@Test
	void testSaldoCuenta() {
		Cuenta cuenta = new Cuenta("Luis", new BigDecimal(1000.123));
		
		assertEquals(1000.123, cuenta.getSaldo().doubleValue());
		assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0); //verifica si es falso, es decir, si se cumple esta bien
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0); //hace lo inverso que la linea de arriba
		
	}
	
	/**
	 * 3. test driven TDD para verficar si son las misma instancia
	 */
	@Test
	void testReferenciaCuenta() {
		//Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1111.1234"));
		Cuenta cuenta2 = new Cuenta("Luis", new BigDecimal("1000.1234"));
		
		//assertNotEquals(cuenta, cuenta2); //verifican si son las misma instancia
		
		assertEquals(cuenta, cuenta2); //son la misma instancia por medio del metodo equals sobreescrito en la clase Cuenta
		
	}
	
	/**
	 * 4. test driven TDD para debito
	 */
	@Test
	void testDebitoCuenta() {
		//Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.1234"));
		cuenta.debito(new BigDecimal(100)); //descontamos 100
		assertNotNull(cuenta.getSaldo()); //evita que el saldo sea nulo
		assertEquals(900, cuenta.getSaldo().intValue()); //verifica que sean iguales
		
		assertEquals("900.1234", cuenta.getSaldo().toPlainString()); //compara el valor que cambio
	}

	
	/**
	 * 5. test driven TDD para credito
	 */
	@Test
	void testCreditoCuenta() {
		//Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.1234"));
		cuenta.credito(new BigDecimal(100)); //agregamos 100
		assertNotNull(cuenta.getSaldo()); //evita que el saldo sea nulo
		assertEquals(1100, cuenta.getSaldo().intValue()); //verifica que sean iguales
		
		assertEquals("1100.1234", cuenta.getSaldo().toPlainString()); //compara el valor que cambio
	}

	/**
	 * 6. test aplicando una excepcion para evitar que el monto al descontar sea <= 0
	 */
	@Test
	void testDineroInsuficienteExceptionCuenta() {
		//Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.1234"));
		
		//se trabaja la excepcion con una expresion lambda para obtener el mensaje
		Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
			cuenta.debito(new BigDecimal(1500));
		});
		
		String actual = exception.getMessage(); //obtenemos el mensaje de la excepcion
		String esperado = "Dinero Insuficiente"; 
		assertEquals(esperado, actual); //verificamos el mensaje de la excepcion
	}
	
	/**
	 * 7. test transferir dinero con ayuda de la calse Banco
	 */
	@Test
	void testTransferirDineroCuentas(){
		//Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.1234"));
		Cuenta cuenta2 = new Cuenta("Luis", new BigDecimal("1000.1234"));
		
		Banco banco = new Banco();
		banco.setNombre("Banco Estado");
		banco.transferir(cuenta, cuenta2, new BigDecimal(500)); //transfiere de cuenta a cuenta2
		
		assertEquals("1500.1234", cuenta2.getSaldo().toPlainString());
		assertEquals("500.1234", cuenta.getSaldo().toPlainString());
	}
	
	/**
	 * 8. test para relacionar las cuentas
	 */
	@Test
	@Disabled //dehabilita el test
	void testRelacionesBancoCuentas(){
		
		fail(); //fuerza el fail en el test
		
		Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.1234"));
		Cuenta cuenta2 = new Cuenta("Daniel", new BigDecimal("1000.1234"));
		
		Banco banco = new Banco();
		
		//aqui ocurre la relacion
		banco.addCuenta(cuenta);
		banco.addCuenta(cuenta2);
		
		banco.setNombre("Banco Estado");
		banco.transferir(cuenta, cuenta2, new BigDecimal(500)); //transfiere de cuenta a cuenta2
		
		
		/*
		 * Utilizando assertAll para ejecutar varios asserts
		 */
		assertAll(
			() -> assertEquals("1500.1234", cuenta2.getSaldo().toPlainString()),
			() -> assertEquals("500.1234", cuenta.getSaldo().toPlainString()),
			() -> assertEquals(2, banco.getCuentas().size()),
			() -> assertEquals("Banco Estado", banco.getNombre())
		);
		
		
		assertEquals("1500.1234", cuenta2.getSaldo().toPlainString());
		assertEquals("500.1234", cuenta.getSaldo().toPlainString());
		
		assertEquals(2, banco.getCuentas().size()); //verificamos si es verdad
		assertEquals("Banco Estado", banco.getNombre()); //verifica el nombre del banco
		/*assertEquals("Luis", banco.getCuentas()
				.stream()
				.filter(c -> c.getPersona().equals("Luis")).findFirst());*/ //verifica que existe una cuenta con el nombre Luis
		
		
		assertTrue(banco.getCuentas()
				.stream()
				.anyMatch(c -> c.getPersona().equals("Daniel"))); //verifica que existe una cuenta con el nombre Daniel
		
		
	}
	
	@Nested //la anaotacion@Nested nos permite anidar varios test por medio de una clase, esto asuminedo que estan en el mismo contexto
	class SistemaOperativoTest {
		  
		/*
		 * 10.1 test que se ejecuta solo en windows
		 */
	    @Test
	    @EnabledOnOs(OS.WINDOWS) //este metodo se ejecuta solo en windows
	    void testSoloWindows() {
	    }

		/*
		 * 10.2 test que se ejecuta en linuuy mac
		 */
	    @Test
	    @EnabledOnOs({OS.LINUX, OS.MAC})
	    void testSoloLinuxMac() {
	    }

		/*
		 * 10.3 test deshabilitado en windows
		 */
	    @Test
	    @DisabledOnOs(OS.WINDOWS)
	    void testNoWindows() {
	    }
	}

	@Nested
	class JavaVersionTest {
		
		/**
		 * 11.1 Test que se ejecuta solo en la version 8
		 */
	    @Test
	    @EnabledOnJre(JRE.JAVA_8)
	    void soloJdk8() {
	    }
		 
		/**
		 * 11.1 Test que se ejecuta solo en la version15
		 */
	    @Test
	    @EnabledOnJre(JRE.JAVA_15)
	    void soloJDK15() {
	    }
		 
		/**
		 * 11.1 Test que se ejecuta para deshabilitar java15
		 */
	    @Test
	    @DisabledOnJre(JRE.JAVA_15)
	    void testNoJDK15() {
	    }
	}

	@Nested
	class SistemPropertiesTest{
	   /**
	    * 12.1 test para imprimir las propiedades del sistema
	    */
	   @Test
	   void imprimirSystemProperties() {
	       Properties properties = System.getProperties();
	       properties.forEach((k, v)-> System.out.println(k + ":" + v));
	   }

	   /**
	    * 12.2 test que se ejecuta en cierta version de java 
	    */
	   @Test
	   @EnabledIfSystemProperty(named = "java.version", matches = ".*15.*")
	   void testJavaVersion() {
	   }

	   /**
	    * 12.3 test que se deshabilita en un sistema de 32 bits
	    */
	   @Test
	   @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
	   void testSolo64() {
	   }

	   /**
	    * 12.4 test habilitado en un sistema de 32 bits
	    */
	   @Test
	   @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
	   void testNO64() {
	   }

	   /**
	    * 12.5 test habilitado con cierto nombre de usuario
	    */
	   @Test
	   @EnabledIfSystemProperty(named = "user.name", matches = "aguzm")
	   void testUsername() {
	   }

	   /**
	    * 12.6 test habilitado si hay una variable de entorno llamada dev
	    */
	   @Test
	   @EnabledIfSystemProperty(named = "ENV", matches = "dev")
	   void testDev() {
	   }
	}

	@Nested
	class VariableAmbienteTest{
		
		/**
		 * 13.1 test que imprime las variables de ambiente 
		 */
	   @Test
	   void imprimirVariablesAmbiente() {
	       Map<String, String> getenv = System.getenv();
	       getenv.forEach((k, v)-> System.out.println(k + " = " + v));
	   }
       
		/**
		 * 13.2 test que esta habilitada si hay una variable con cierto nombre 
		 */
	   @Test
	   @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-15.0.1.*")
	   void testJavaHome() {
	   }
       
		/**
		 * 13.3 test que esta habilitada si hay una variable con cierto nombre 
		 */
	   @Test
	   @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
	   void testProcesadores() {
	   }
       
		/**
		 * 13.4 test que esta habilitada si hay una variable con cierto nombre 
		 */
	   @Test
	   @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
	   void testEnv() {
	   }
       
		/**
		 * 13.4 test que esta habilitada si hay una variable con cierto nombre 
		 */
	   @Test
	   @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "prod")
	   void testEnvProdDisabled() {
	   }
	}  
       
	
	/**
	 * 14
	 */
    @Test
    @DisplayName("test Saldo Cuenta Dev")
    void testSaldoCuentaDev() {
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(esDev);
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }
	   
	/**
	 * 15. Dependiendo de una condicion ejecutara cierto codigo
	 */
    @Test
    @DisplayName("test Saldo Cuenta Dev 2")
    void testSaldoCuentaDev2() {
        boolean esDev = "dev".equals(System.getProperty("ENV")); //taremos esta variable de ambiente
        //en caso de que se true, entra a la expresion labda y ademas eejcuta los assertions
        assumingThat(esDev, () -> {
            assertNotNull(cuenta.getSaldo());
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        });
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }
	
	
	/**
	 * 16. test que repite n veces una accion
	 */
    @DisplayName("Probando Debito Cuenta Repetir!")
    //las variables de repeticion se ven reflejadas en la sig anotacion
    @RepeatedTest(value=5, name = "{displayName} - Repetición numero {currentRepetition} de {totalRepetitions}")
    void testDebitoCuentaRepetir(RepetitionInfo info) {
        if(info.getCurrentRepetition() == 3){
            System.out.println("estamos en la repeticion " + info.getCurrentRepetition());
        }
        cuenta.debito(new BigDecimal(100)); //descontamos 100
		assertNotNull(cuenta.getSaldo()); //evita que el saldo sea nulo
		assertEquals(900, cuenta.getSaldo().intValue()); //verifica que sean iguales
		
		assertEquals("900.1234", cuenta.getSaldo().toPlainString()); //compara el valor que cambio
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

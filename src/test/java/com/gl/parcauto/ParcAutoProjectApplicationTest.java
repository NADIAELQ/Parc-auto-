package com.gl.parcauto;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ParcAutoProjectApplicationTest {
    /**
     * Method under test: {@link ParcAutoProjectApplication#run(String[])}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRun() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.gl.parcauto.repository.RoleRepository.findAll()" because "this.roleRepository" is null
        //       at com.gl.parcauto.ParcAutoProjectApplication.addRoles(ParcAutoProjectApplication.java:37)
        //       at com.gl.parcauto.ParcAutoProjectApplication.run(ParcAutoProjectApplication.java:33)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ParcAutoProjectApplication()).run("Args");
    }

    /**
     * Method under test: {@link ParcAutoProjectApplication#addRoles()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddRoles() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.gl.parcauto.repository.RoleRepository.findAll()" because "this.roleRepository" is null
        //       at com.gl.parcauto.ParcAutoProjectApplication.addRoles(ParcAutoProjectApplication.java:37)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ParcAutoProjectApplication()).addRoles();
    }

    /**
     * Method under test: {@link ParcAutoProjectApplication#addUsers()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddUsers() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.gl.parcauto.repository.UserRepository.findAll()" because "this.userRepository" is null
        //       at com.gl.parcauto.ParcAutoProjectApplication.addUsers(ParcAutoProjectApplication.java:48)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ParcAutoProjectApplication()).addUsers();
    }
}
